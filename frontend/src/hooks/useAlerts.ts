import { useState } from "react";
import { getAlerts, type FraudAlert } from "../services/FraudService";

interface UseAlertsReturn {
  alerts: FraudAlert[];
  isLoading: boolean;
  error: string | null;
  refetch: () => Promise<void>;
}

export const useAlerts = (): UseAlertsReturn => {
  const [alerts, setAlerts] = useState<FraudAlert[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchAlerts = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const data = await getAlerts();
      setAlerts(data);
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Error cargando las alertas de fraude"
      );
    } finally {
      setIsLoading(false);
    }
  };

  return {
    alerts,
    isLoading,
    error,
    refetch: fetchAlerts,
  };
};
