import torch
import torch.nn as nn
import pytorch_lightning as pl
from torch.utils.data import DataLoader, TensorDataset
import mlflow


class ToyModel(pl.LightningModule):
    def __init__(self, num_classes: int = 10):
        super().__init__()
        self.net = nn.Sequential(
            nn.Linear(3 * 32 * 32, 128),
            nn.ReLU(),
            nn.Linear(128, num_classes),
        )
        self.loss_fn = nn.CrossEntropyLoss()

    def forward(self, x: torch.Tensor) -> torch.Tensor:
        return self.net(x.flatten(start_dim=1))

    def training_step(self, batch: tuple, _batch_idx: int) -> torch.Tensor:
        x, y = batch
        loss = self.loss_fn(self(x), y)
        self.log("train_loss", loss, prog_bar=True)
        return loss

    def configure_optimizers(self):
        return torch.optim.Adam(self.parameters(), lr=1e-3)


def train(max_epochs: int = 3) -> None:
    mlflow.set_experiment("lifelens_toy")
    with mlflow.start_run():
        mlflow.log_param("max_epochs", max_epochs)

        x = torch.randn(100, 3, 32, 32)
        y = torch.randint(0, 10, (100,))
        loader = DataLoader(TensorDataset(x, y), batch_size=16)

        model = ToyModel()
        trainer = pl.Trainer(max_epochs=max_epochs, enable_progress_bar=True)
        trainer.fit(model, loader)

        mlflow.log_metric("final_epoch", max_epochs)
        print("Training complete.")


if __name__ == "__main__":
    train()
