# Fraud Detection Frontend

Frontend application for the Fraud Detection Service built with React, TypeScript, and TailwindCSS.

## Features

- ✅ Evaluate transactions for fraud risk
- ✅ View recent evaluation history
- ✅ Responsive design with TailwindCSS
- ✅ React Router for navigation
- ✅ TypeScript for type safety

## Tech Stack

- React 19
- TypeScript
- Vite
- TailwindCSS
- React Router
- Axios

## Development

### Prerequisites

- Node.js 20+
- npm

### Installation

```bash
npm install
```

### Environment Variables

Copy `.env.example` to `.env` and configure:

```bash
cp .env.example .env
```

```
VITE_API_BASE_URL=http://localhost:8080
```

### Run Development Server

```bash
npm run dev
```

The app will be available at `http://localhost:3000`

### Build for Production

```bash
npm run build
```

### Preview Production Build

```bash
npm run preview
```

## Docker

### Build and Run with Docker Compose

From the project root:

```bash
docker-compose up --build
```

The frontend will be available at `http://localhost:3000`

### Build Docker Image

```bash
docker build -t fraud-frontend .
```

### Run Docker Container

```bash
docker run -p 3000:80 -e VITE_API_BASE_URL=http://localhost:8080 fraud-frontend
```

## Project Structure

```
src/
├── components/       # Reusable components
├── pages/           # Page components
│   ├── EvaluateTransaction.tsx
│   └── RecentEvaluations.tsx
├── services/        # API services
│   └── FraudService.ts
├── App.tsx          # Main app with routing
└── main.tsx         # Entry point
```

## Available Pages

- `/` - Evaluate Transaction
- `/history` - Recent Evaluations History

## API Endpoints

The frontend consumes the following backend endpoints:

- `POST /api/v1/fraud/evaluate` - Evaluate a transaction
- `GET /api/v1/fraud/evaluations?limit=10` - Get recent evaluations

import reactDom from 'eslint-plugin-react-dom'

export default defineConfig([
  globalIgnores(['dist']),
  {
    files: ['**/*.{ts,tsx}'],
    extends: [
      // Other configs...
      // Enable lint rules for React
      reactX.configs['recommended-typescript'],
      // Enable lint rules for React DOM
      reactDom.configs.recommended,
    ],
    languageOptions: {
      parserOptions: {
        project: ['./tsconfig.node.json', './tsconfig.app.json'],
        tsconfigRootDir: import.meta.dirname,
      },
      // other options...
    },
  },
])
```
