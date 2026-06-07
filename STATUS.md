# LifeLens — Project Status

## Current Phase
Phase 1 complete — Android app running on Galaxy S25 Ultra.
Phase 2 (classifier training) not started — collecting labeled photos first.

## What's Done
- **Phase 0**: Full project structure, Python ML tooling (uv, PyTorch, Lightning, MLflow, DVC, Ruff, Pytest, GitHub Actions CI)
- **Phase 1**: Android app (Kotlin + Jetpack Compose) deployed on device
  - Camera tab: CameraX live preview, shutter button, saves JPGs to internal storage
  - Post-capture preview screen: full-screen photo + label chips, Done/back saves and returns to camera
  - Gallery tab: 3-column grid, tap to re-label any photo
  - Room DB: stores file path, timestamp, label per photo

## Activity Labels (9)
Food · Fitness · Work · Travel · Social · Learning · Outdoors · Gaming · Leisure

## Decisions Made
- Android first (Galaxy S25 Ultra), iOS migration later
- minSdk 26 (Android 8.0+), targetSdk 35
- Dropped "Pets" and "Other"; added "Leisure"
- Photos saved to internal app storage (privacy-first, no media library access needed)

## Next Steps
1. **Collect data** — use the app daily, label photos across all 9 categories
2. **Phase 2** — once enough labeled photos exist, train EfficientNet/ConvNeXt classifier
   - PyTorch Lightning training pipeline
   - Accuracy / Precision / Recall / F1 metrics
   - Export model checkpoint
3. **Phase 3** — ONNX export, quantization, on-device inference (<100ms target)

## Stack
| Layer | Tech |
|-------|------|
| Android | Kotlin, Jetpack Compose, CameraX, Room, Coil |
| Build | AGP 8.13.2, Kotlin 2.0.21, Gradle 8.13 |
| ML | PyTorch 2.4+, Lightning, MLflow, DVC |
| Python env | uv |
