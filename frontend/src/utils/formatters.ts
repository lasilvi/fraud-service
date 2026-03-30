import type { EvaluationHistoryItem } from "../services/FraudService";

export const formatCurrency = (amount: number): string => {
  return new Intl.NumberFormat("es-CO", {
    style: "currency",
    currency: "COP",
    minimumFractionDigits: 0,
  }).format(amount);
};

export const formatDate = (date: string | Date): string => {
  const dateObj = typeof date === "string" ? new Date(date) : date;
  return new Intl.DateTimeFormat("es-CO", {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  }).format(dateObj);
};

export const getRiskLevelVariant = (
  riskLevel: string
): "success" | "warning" | "danger" | "neutral" => {
  switch (riskLevel.toUpperCase()) {
    case "HIGH":
      return "danger";
    case "MEDIUM":
      return "warning";
    case "LOW":
      return "success";
    default:
      return "neutral";
  }
};

export const getSuspiciousVariant = (
  suspicious: boolean
): "danger" | "success" => {
  return suspicious ? "danger" : "success";
};

export const groupEvaluationsByDate = (
  evaluations: EvaluationHistoryItem[]
): Record<string, EvaluationHistoryItem[]> => {
  return evaluations.reduce(
    (acc, evaluation) => {
      if (!evaluation.evaluatedAt) return acc;

      const date = new Date(evaluation.evaluatedAt).toLocaleDateString("es-CO");
      if (!acc[date]) {
        acc[date] = [];
      }
      acc[date].push(evaluation);
      return acc;
    },
    {} as Record<string, EvaluationHistoryItem[]>
  );
};
