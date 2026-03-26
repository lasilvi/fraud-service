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

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError("");
    setResult(null);

    try {
      const data = await evaluateTransaction({
        amount,
        transactionCountry,
        userCountry,
      });
      setResult(data);
    } catch {
      setError("Error evaluando la transacción");
    }
  };

  return (
    <div style={{ maxWidth: 520, margin: "40px auto", fontFamily: "sans-serif" }}>
      <h1>Fraud Evaluation</h1>
      <form onSubmit={handleSubmit} style={{ display: "grid", gap: 12 }}>
        <label>
          Amount
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(Number(e.target.value))}
            required
          />
        </label>

        <label>
          Transaction Country
          <input
            type="text"
            value={transactionCountry}
            onChange={(e) => setTransactionCountry(e.target.value.toUpperCase())}
            required
          />
        </label>

        <label>
          User Country
          <input
            type="text"
            value={userCountry}
            onChange={(e) => setUserCountry(e.target.value.toUpperCase())}
            required
          />
        </label>

        <button type="submit">Evaluate</button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {result && (
        <div style={{ marginTop: 20 }}>
          <h2>Resultado</h2>
          <p>
            <strong>Suspicious:</strong> {String(result.suspicious)}
          </p>
          <p>
            <strong>Risk Level:</strong> {result.riskLevel}
          </p>
          <p>
            <strong>Reasons:</strong> {result.reasons.join(", ")}
          </p>
        </div>
      )}
    </div>
  );
};

export default EvaluateTransaction;
