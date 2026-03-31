export const COUNTRY_CODES = [
  { code: "CO", name: "Colombia" },
  { code: "US", name: "Estados Unidos" },
  { code: "MX", name: "México" },
  { code: "AR", name: "Argentina" },
  { code: "BR", name: "Brasil" },
  { code: "CL", name: "Chile" },
  { code: "PE", name: "Perú" },
  { code: "ES", name: "España" },
  { code: "GB", name: "Reino Unido" },
  { code: "FR", name: "Francia" },
  { code: "DE", name: "Alemania" },
  { code: "IT", name: "Italia" },
  { code: "CN", name: "China" },
  { code: "JP", name: "Japón" },
  { code: "IN", name: "India" },
];

export const FRAUD_THRESHOLDS = {
  LOW_RISK: 5000,
  MEDIUM_RISK: 10000,
  HIGH_RISK: 15000,
} as const;

export const RISK_LEVELS = {
  LOW: "LOW",
  MEDIUM: "MEDIUM",
  HIGH: "HIGH",
} as const;

export const DEFAULT_EVALUATION_LIMIT = 10;

export const ROUTES = {
  HOME: "/",
  EVALUATE: "/",
  HISTORY: "/history",
  DASHBOARD: "/dashboard",
  CONFIG: "/config",
} as const;
