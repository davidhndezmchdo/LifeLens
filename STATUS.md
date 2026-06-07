# LifeLens — Project Status

## Current Phase
**Data collection** — app deployed on Galaxy S25 Ultra, actively gathering labeled photos.
Target: 100–200 photos per category before starting Phase 2 training.

## What's Done
- **Phase 0**: Full project structure, Python ML tooling (uv, PyTorch, Lightning, MLflow, DVC, Ruff, Pytest, GitHub Actions CI)
- **Phase 1**: Android app (Kotlin + Jetpack Compose) deployed and running
  - Camera tab: CameraX live preview, shutter button, post-capture label screen
  - Gallery tab: 3-column grid, tap any photo to re-label
  - **Import**: FAB on Gallery tab opens system photo picker (multi-select), bulk-label entire batch, copies to internal storage
  - Room DB: stores file path, timestamp, label per photo
- **Tooling**: `/update-project` slash command, STATUS.md, Claude memory, git repo initialised

## Activity Labels (9)
Food · Fitness · Work · Travel · Social · Learning · Outdoors · Gaming · Leisure

## Decisions Made
- Android first (Galaxy S25 Ultra), iOS migration later
- minSdk 26 (Android 8.0+), targetSdk 35
- Dropped "Pets" and "Other"; added "Leisure"
- Photos saved to internal app storage (privacy-first, no media library access needed)
- Import copies photos into app storage (not just URI references) for consistency
- Target 100–200 labeled photos per category before Phase 2; balance matters more than total count

## Build Versions (as of 2026-06-08)
| Component | Version |
|-----------|---------|
| AGP | 9.2.1 |
| Kotlin | 2.2.10 |
| KSP | 2.3.2 |
| Room | 2.7.1 |
| Compose BOM | 2024.09.03 |

## Next Steps
1. **Collect data** — use camera + import daily, aim for balance across all 9 labels
2. **Phase 2** — EfficientNet/ConvNeXt classifier with PyTorch Lightning
   - Export photos from device → `datasets/` folder
   - Training pipeline, Accuracy / Precision / Recall / F1
   - Export model checkpoint
3. **Phase 3** — ONNX export, quantization, on-device inference (<100ms target)
