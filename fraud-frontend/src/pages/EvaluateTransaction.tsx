import React, { FormEvent, useState } from "react";
import {
  evaluateTransaction,
  EvaluateResponse,
} from "../services/FraudService";

const EvaluateTransaction: React.FC = () => {
  const [amount, setAmount] = useState<number>(0);
  const [transactionCountry, setTransactionCountry] = useState<string>("US");
  const [userCountry, setUserCountry] = useState<string>("CO");
  const [result, setResult] = useState<EvaluateResponse | null>(null);
  const [error, setError] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError("");
    setResult(null);
    setLoading(true);

    try {
      const data = await evaluateTransaction({
        amount,
        transactionCountry,
        userCountry,
      });
      setResult(data);
    } catch {
      setError("Error evaluando la transacción");
    } finally {
      setLoading(false);
    }
  };

  const getRiskLevelColor = (riskLevel: string) => {
    switch (riskLevel.toUpperCase()) {
      case "HIGH":
        return "bg-red-100 text-red-800 border-red-200";
      case "MEDIUM":
        return "bg-yellow-100 text-yellow-800 border-yellow-200";
      case "LOW":
        return "bg-green-100 text-green-800 border-green-200";
      default:
        return "bg-gray-100 text-gray-800 border-gray-200";
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">
        Evaluación de Transacciones
      </h1>

      <form
        onSubmit={handleSubmit}
        className="bg-white rounded-lg shadow-md p-6 space-y-6"
      >
        <div>
          <label
            htmlFor="amount"
            className="block text-sm font-medium text-gray-700 mb-2"
          >
            Monto
          </label>
          <input
            id="amount"
            type="number"
            value={amount}
            onChange={(e) => setAmount(Number(e.target.value))}
            required
            min="0"
            step="0.01"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition"
            placeholder="Ingrese el monto"
          />
        </div>

        <div>
          <label
            htmlFor="transactionCountry"
            className="block text-sm font-medium text-gray-700 mb-2"
          >
            País de la Transacción
          </label>
          <input
            id="transactionCountry"
            type="text"
            value={transactionCountry}
            onChange={(e) => setTransactionCountry(e.target.value.toUpperCase())}
            required
            maxLength={2}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition uppercase"
            placeholder="US"
          />
        </div>

        <div>
          <label
            htmlFor="userCountry"
            className="block text-sm font-medium text-gray-700 mb-2"
          >
            País del Usuario
          </label>
          <input
            id="userCountry"
            type="text"
            value={userCountry}
            onChange={(e) => setUserCountry(e.target.value.toUpperCase())}
            required
            maxLength={2}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition uppercase"
            placeholder="CO"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-purple-600 text-white py-2 px-4 rounded-md hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition font-medium"
        >
          {loading ? "Evaluando..." : "Evaluar Transacción"}
        </button>
      </form>

      {error && (
        <div className="mt-6 bg-red-50 border border-red-200 rounded-lg p-4">
          <p className="text-red-800 font-medium">{error}</p>
        </div>
      )}

      {result && (
        <div className="mt-6 bg-white rounded-lg shadow-md p-6">
          <h2 className="text-2xl font-bold text-gray-900 mb-4">Resultado</h2>

          <div className="space-y-4">
            <div className="flex items-center">
              <span className="text-gray-700 font-medium w-32">
                Sospechoso:
              </span>
              {result.suspicious ? (
                <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-red-100 text-red-800">
                  Sí
                </span>
              ) : (
                <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
                  No
                </span>
              )}
            </div>

            <div className="flex items-center">
              <span className="text-gray-700 font-medium w-32">
                Nivel de Riesgo:
              </span>
              <span
                className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium border ${getRiskLevelColor(
                  result.riskLevel
                )}`}
              >
                {result.riskLevel}
              </span>
            </div>

            <div>
              <span className="text-gray-700 font-medium block mb-2">
                Razones:
              </span>
              {result.reasons.length > 0 ? (
                <ul className="list-disc list-inside space-y-1 text-gray-600">
                  {result.reasons.map((reason, index) => (
                    <li key={index}>{reason}</li>
                  ))}
                </ul>
              ) : (
                <p className="text-gray-500 italic">Sin razones detectadas</p>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default EvaluateTransaction;
