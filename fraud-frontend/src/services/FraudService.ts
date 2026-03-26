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

const API_BASE_URL =
  process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";

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
