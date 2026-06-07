# LifeLens

An Android application that understands and reflects on your life through photos.

LifeLens lets you capture or import photos throughout the day, then uses machine learning to organize them into meaningful clusters, discover activity patterns, and generate visual summaries of your weeks, months, and years — all while keeping your data private and running primarily on-device.

---

## What it does

- **Capture & label** photos directly from the camera or your gallery
- **Classify** photos into activity categories (Food, Fitness, Work, Travel, Social, Learning, Outdoors, Gaming, Leisure)
- **Discover patterns** through unsupervised clustering of learned embeddings
- **Generate summaries** — weekly and monthly "Life Wrapped" insights
- **Create memory cards** — automatic visual collages of periods and themes
- **Learn preferences** — improve organization using your feedback over time

Everything is designed to work without requiring location, calendar, or any personal data beyond photos and timestamps.

---

## Architecture

```
LifeLens/
├── android-app/     # Kotlin + Jetpack Compose (Phase 1+)
├── backend/         # FastAPI service layer
├── training/        # PyTorch Lightning training pipelines
├── models/          # Exported checkpoints and ONNX models
├── datasets/        # Labeled photo datasets (gitignored)
├── experiments/     # MLflow experiment tracking
├── notebooks/       # Exploration and visualization
├── scripts/         # Utility scripts
├── tests/           # Python test suite
└── docs/            # Documentation
```

---

## Tech Stack

| Layer | Tools |
|-------|-------|
| Android | Kotlin, Jetpack Compose, CameraX, Room, KSP |
| ML Framework | PyTorch, PyTorch Lightning |
| Experiment Tracking | MLflow, DVC |
| Model Export | ONNX, quantization |
| Python Tooling | uv, Ruff, Pytest, pre-commit |
| CI | GitHub Actions |

**Target device:** Samsung Galaxy S25 Ultra (Android 8.0+ / minSdk 26)

---

## Roadmap

| Phase | Description | Status |
|-------|-------------|--------|
| 0 | Project setup & ML tooling | Done |
| 1 | Android photo collection app | Done |
| 2 | Supervised activity classifier (EfficientNet / ConvNeXt) | Next |
| 3 | On-device ONNX inference (<100ms) | Planned |
| 4 | Self-supervised representation learning (SimCLR / DINO) | Planned |
| 5 | Automatic cluster discovery (HDBSCAN) | Planned |
| 6 | Life Wrapped — insights & summaries | Planned |
| 7 | Visual memory card generation | Planned |
| 8 | User feedback learning | Planned |
| 9 | Knowledge distillation & mobile optimization | Planned |
| 10 | Generative memory system (VAE / embeddings) | Research |

---

## Getting Started

### Prerequisites

- [uv](https://docs.astral.sh/uv/) — Python package manager
- Python 3.13+
- Android Studio (for the Android app)

### Python environment

```bash
uv sync
```

### Run tests

```bash
uv run pytest
```

### Train the toy model

```bash
uv run python training/train.py
```

### Android app

Open `android-app/` in Android Studio and run on a connected device or emulator (API 26+).

---

## License

Copyright (c) 2026 David Hernandez. All Rights Reserved. See [LICENSE](LICENSE) for details.
