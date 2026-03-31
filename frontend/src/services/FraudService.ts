export type EvaluateRequest = {
  amount: number;
  transactionCountry: string;
  userCountry: string;
};

export type EvaluateResponse = {
  suspicious: boolean;
  riskLevel: string;
  reasons: string[];
};

export type EvaluationHistoryItem = {
  suspicious: boolean;
  riskLevel: string;
  reasons: string[];
  evaluatedAt?: string;
  amount?: number;
  transactionCountry?: string;
  userCountry?: string;
};

export type ThresholdConfigResponse = {
  threshold: number;
};

export type ThresholdConfigRequest = {
  threshold: number;
};

export type UserLocationConfigResponse = {
  userId: string;
  usualCountry: string;
};

export type UserLocationConfigRequest = {
  userId: string;
  usualCountry: string;
};

// Use relative URL in production (nginx proxy), absolute in development
const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL || "";

export async function evaluateTransaction(
  payload: EvaluateRequest
): Promise<EvaluateResponse> {
  const response = await fetch(`${API_BASE_URL}/api/v1/fraud/evaluate`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error("No se pudo evaluar la transacción");
  }

  return response.json();
}

export async function getRecentEvaluations(
  limit = 10
): Promise<EvaluationHistoryItem[]> {
  const response = await fetch(
    `${API_BASE_URL}/api/v1/fraud/evaluations?limit=${limit}`,
    {
      method: "GET",
    }
  );

  if (!response.ok) {
    throw new Error("No se pudo obtener el historial de evaluaciones");
  }

  return response.json();
}
