# LifeLens

## Vision

LifeLens is an Android application that helps users understand and reflect on their lives through photos.

Users upload photos naturally throughout the day. The system learns semantic representations of those photos, organizes them into meaningful clusters, generates insights about habits and activities, and eventually creates visual summaries of weeks, months, and years.

The long-term goal is to build a personalized multimodal representation of a person's life that can:

- Categorize activities
- Discover recurring patterns
- Generate monthly "Life Wrapped" summaries
- Create visual memory cards
- Learn user preferences over time
- Run primarily on-device

This project prioritizes:

1. Machine Learning
2. Computer Vision
3. Representation Learning
4. Mobile Deployment
5. MLOps
6. User Privacy

The project should be developed incrementally, with each phase producing a usable artifact before moving to the next phase.

---

# Guiding Principles

## Privacy First

The system should avoid collecting unnecessary personal information.

Preferred inputs:

- Photos
- Timestamps

Optional future inputs:

- Location
- Calendar events
- Activity data

All features should remain functional without additional personal data.

---

## ML First

The goal is not simply to build a photo gallery.

The goal is to train and deploy machine learning systems that understand and organize life events.

---

## Iterative Development

Every phase should result in:

- Working code
- Tests
- Documentation
- Demo artifacts

No phase should depend on a future phase.

---

# Phase 0: Project Setup

## Goal

Create a professional ML project structure.

## Deliverables

Repository structure:

text lifelens/ ├── android-app/ ├── backend/ ├── training/ ├── datasets/ ├── experiments/ ├── notebooks/ ├── docs/ ├── tests/ ├── models/ └── scripts/ 

Setup:

- uv
- PyTorch
- PyTorch Lightning
- MLflow
- DVC
- Ruff
- Pytest
- Pre-commit hooks
- GitHub Actions
- Disributed Training using Kaggle
- Databricks Free Version for Model Lifecycle

## Success Criteria

Repository can:

- Run tests
- Train a toy model
- Track experiments

---

# Phase 1: Photo Collection Application

## Goal

Create the simplest Android application possible.

Users should be able to:

- Upload/Take photos
- Store metadata
- Browse uploaded images
- Label the photos (will be for initial labelling and HITL in the future)

## Deliverables

Android app:

- Photo upload
- Local storage
- Gallery view

Metadata:

- Timestamp
- Image ID

## Success Criteria

Store and retrieve photos successfully.

No ML yet.

---

# Phase 2: Baseline Activity Classification

## Goal

Create a supervised classifier.

## Initial Labels

- Food
- Fitness
- Work
- Travel
- Social
- Learning
- Outdoors
- Gaming
- Pets
- Other

## Model

Start simple:

- EfficientNet
- ConvNeXt Tiny

PyTorch Lightning only.

## Deliverables

Training pipeline.

Metrics:

- Accuracy
- Precision
- Recall
- F1

## Success Criteria

Train and evaluate successfully.

Export model checkpoint.

---

# Phase 3: On-Device Inference

## Goal

Deploy the classifier.

## Tasks

Export:

- ONNX

Optimize:

- Quantization
- Mobile inference

Android integration.

## Deliverables

App predicts categories locally.

## Success Criteria

Prediction latency under 100 ms on target device.

---

# Phase 4: Representation Learning

## Goal

Learn a semantic embedding space.

Instead of predicting labels:

text Image -> Embedding 

## Candidate Methods

Evaluate:

- SimCLR
- BYOL
- DINO
- VICReg

## Deliverables

Embedding extraction pipeline.

Visualization notebooks:

- PCA
- t-SNE
- UMAP

## Success Criteria

Semantically similar photos cluster together.

---

# Phase 5: Automatic Discovery

## Goal

Create meaningful groups without labels.

## Methods

Clustering:

- K-Means
- HDBSCAN
- Hierarchical Clustering

## Example Clusters

Potentially:

- Coffee shops
- Hiking trips
- Gym sessions
- Screenshots
- Restaurants

## Deliverables

Automatic cluster generation.

Cluster visualizations.

## Success Criteria

Generated clusters are interpretable.

---

# Phase 6: Life Wrapped v1

## Goal

Generate insights from embeddings and classifications.

## Examples

Weekly Summary:

- Activity distribution
- Most common themes
- Most active categories

Monthly Summary:

- Category trends
- New experiences
- Largest clusters

## Deliverables

Dashboard and summary generation.

## Success Criteria

Generate meaningful reports automatically.

---

# Phase 7: Collage Generation

## Goal

Automatically generate visual memory cards.

## Inputs

- Classification outputs
- Cluster assignments
- Representative photos

## Outputs

Examples:

- Month in Review
- Summer Highlights
- Fitness Journey

## Deliverables

Collage generation pipeline.

## Success Criteria

Generate attractive summaries automatically.

No generative AI yet.

---

# Phase 8: User Feedback Learning

## Goal

Improve organization using feedback.

Users can:

- Like clusters
- Dislike clusters
- Merge clusters
- Rename clusters

## Deliverables

Feedback storage system.

Preference learning pipeline.

## Success Criteria

Recommendations improve using feedback.

---

# Phase 9: Distillation and Mobile Optimization

## Goal

Create a lightweight model.

Teacher:

- Larger vision encoder

Student:

- Mobile-friendly architecture

## Metrics

- Accuracy
- Latency
- Model size

## Deliverables

Compressed model.

## Success Criteria

Maintain performance with significantly reduced size.

---

# Phase 10: Generative Memory System

## Goal

Generate synthetic memory representations from learned embeddings.

Research-focused phase.

## Possible Directions

### Option A

Generate memory cards.

Embedding -> Visual summary.

### Option B

Train a VAE.

Image -> Latent -> Reconstruction.

### Option C

Learn month-level embeddings.

Month Embedding -> Generated Memory Poster.

## Deliverables

Prototype generative system.

## Success Criteria

Generated outputs capture themes from the original photo collection.

---

# Stretch Goals

## Multimodal Learning

Additional signals:

- Location
- Calendar
- Activity tracking

Fusion architecture:

text Image Encoder + Metadata Encoder = Life Representation 

## Social Features

Share:

- Monthly wrapped
- Collages
- Visual summaries

## Federated Learning

Explore privacy-preserving personalization.

## Temporal Modeling

Learn routines and life transitions over time.

---

# Ultimate Goal

Build a system that learns meaningful representations of a person's life from photos and transforms those representations into useful insights, summaries, and memories while remaining privacy-conscious and deployable on mobile devices.
