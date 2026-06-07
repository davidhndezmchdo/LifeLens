import torch
from training.train import ToyModel


def test_forward_shape():
    model = ToyModel(num_classes=10)
    x = torch.randn(2, 3, 32, 32)
    out = model(x)
    assert out.shape == (2, 10)


def test_loss_is_positive():
    model = ToyModel(num_classes=10)
    x = torch.randn(4, 3, 32, 32)
    y = torch.randint(0, 10, (4,))
    loss = torch.nn.CrossEntropyLoss()(model(x), y)
    assert loss.item() > 0


def test_different_batch_sizes():
    model = ToyModel(num_classes=10)
    for batch_size in [1, 8, 32]:
        x = torch.randn(batch_size, 3, 32, 32)
        out = model(x)
        assert out.shape == (batch_size, 10)
