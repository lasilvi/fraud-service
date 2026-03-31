import { useState } from "react";
import {
  getThresholdConfig,
  updateThresholdConfig,
  getUserLocationConfig,
  saveUserLocationConfig,
} from "../services/FraudService";

interface ConfigState {
  maxAmountThreshold: number;
  userCountry: string;
}

interface UseConfigReturn {
  config: ConfigState;
  isLoading: boolean;
  isSaving: boolean;
  error: string | null;
  success: string | null;
  fetchConfig: () => Promise<void>;
  saveConfig: (config: ConfigState) => Promise<void>;
}

const DEFAULT_USER_ID = "default";

export const useConfig = (): UseConfigReturn => {
  const [config, setConfig] = useState<ConfigState>({
    maxAmountThreshold: 0,
    userCountry: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [isSaving, setIsSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const fetchConfig = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const [thresholdData, locationData] = await Promise.allSettled([
        getThresholdConfig(),
        getUserLocationConfig(DEFAULT_USER_ID),
      ]);

      setConfig({
        maxAmountThreshold:
          thresholdData.status === "fulfilled" ? thresholdData.value.threshold : 0,
        userCountry:
          locationData.status === "fulfilled" ? locationData.value.usualCountry : "",
      });
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Error cargando la configuración"
      );
    } finally {
      setIsLoading(false);
    }
  };

  const saveConfig = async (newConfig: ConfigState) => {
    setIsSaving(true);
    setError(null);
    setSuccess(null);

    try {
      await Promise.all([
        updateThresholdConfig({ threshold: newConfig.maxAmountThreshold }),
        saveUserLocationConfig({
          userId: DEFAULT_USER_ID,
          usualCountry: newConfig.userCountry,
        }),
      ]);

      setConfig(newConfig);
      setSuccess("Configuración guardada exitosamente");
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Error guardando la configuración"
      );
    } finally {
      setIsSaving(false);
    }
  };

  return { config, isLoading, isSaving, error, success, fetchConfig, saveConfig };
};
