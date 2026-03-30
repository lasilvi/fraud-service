import { useState } from "react";
import {
  evaluateTransaction,
  getRecentEvaluations,
  type EvaluateRequest,
  type EvaluateResponse,
  type EvaluationHistoryItem,
} from "../services/FraudService";

interface UseEvaluateTransactionReturn {
  evaluate: (request: EvaluateRequest) => Promise<void>;
  result: EvaluateResponse | null;
  isLoading: boolean;
  error: string | null;
  reset: () => void;
}

export const useEvaluateTransaction = (): UseEvaluateTransactionReturn => {
  const [result, setResult] = useState<EvaluateResponse | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const evaluate = async (request: EvaluateRequest) => {
    setIsLoading(true);
    setError(null);
    setResult(null);

    try {
      const data = await evaluateTransaction(request);
      setResult(data);
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Error evaluando la transacción"
      );
    } finally {
      setIsLoading(false);
    }
  };

  const reset = () => {
    setResult(null);
    setError(null);
  };

  return { evaluate, result, isLoading, error, reset };
};

interface UseEvaluationHistoryReturn {
  evaluations: EvaluationHistoryItem[];
  isLoading: boolean;
  error: string | null;
  refetch: () => Promise<void>;
}

export const useEvaluationHistory = (
  limit = 10
): UseEvaluationHistoryReturn => {
  const [evaluations, setEvaluations] = useState<EvaluationHistoryItem[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchEvaluations = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const data = await getRecentEvaluations(limit);
      setEvaluations(data);
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Error cargando el historial de evaluaciones"
      );
    } finally {
      setIsLoading(false);
    }
  };

  return {
    evaluations,
    isLoading,
    error,
    refetch: fetchEvaluations,
  };
};
