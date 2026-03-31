export type EvaluateRequest = {
  userId: string;
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

export async function getThresholdConfig(): Promise<ThresholdConfigResponse> {
  const response = await fetch(`${API_BASE_URL}/api/v1/config/threshold`);

  if (!response.ok) {
    throw new Error("No se pudo obtener la configuración del umbral");
  }

  return response.json();
}

export async function updateThresholdConfig(
  payload: ThresholdConfigRequest
): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/api/v1/config/threshold`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error("No se pudo actualizar el umbral");
  }
}

export async function getUserLocationConfig(
  userId: string
): Promise<UserLocationConfigResponse> {
  const response = await fetch(
    `${API_BASE_URL}/api/v1/config/user-location/${encodeURIComponent(userId)}`
  );

  if (!response.ok) {
    throw new Error("No se pudo obtener la ubicación del usuario");
  }

  return response.json();
}

export async function saveUserLocationConfig(
  payload: UserLocationConfigRequest
): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/api/v1/config/user-location`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error("No se pudo guardar la ubicación del usuario");
  }
}
