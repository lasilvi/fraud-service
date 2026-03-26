import { type FormEvent, useState } from "react";
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardFooter,
  Input,
  Badge,
  PageHeader,
} from "../components";
import { useEvaluateTransaction } from "../hooks";
import { formatCurrency, getRiskLevelVariant, getSuspiciousVariant, COUNTRY_CODES } from "../utils";
import type { EvaluateRequest } from "../services/FraudService";

const EvaluateTransaction = () => {
  const [formData, setFormData] = useState<EvaluateRequest>({
    amount: 0,
    transactionCountry: "US",
    userCountry: "CO",
  });

  const { evaluate, result, isLoading, error, reset } = useEvaluateTransaction();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await evaluate(formData);
  };

  const handleInputChange = (field: keyof EvaluateRequest, value: string | number) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleReset = () => {
    setFormData({ amount: 0, transactionCountry: "US", userCountry: "CO" });
    reset();
  };

  return (
    <div className="max-w-6xl mx-auto animate-fade-in">
      <PageHeader
        title="Evaluación de Transacciones"
        subtitle="Analiza transacciones en tiempo real con inteligencia artificial para detectar posibles fraudes"
        icon={
          <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
          </svg>
        }
      />

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Form Card - Modernizado */}
        <Card shadow="lg" hoverable>
          <CardHeader>
            <CardTitle size="lg">Datos de la Transacción</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-5">
              {/* Input con icono de moneda */}
              <Input
                label="Monto de la Transacción"
                type="number"
                value={formData.amount}
                onChange={(e) => handleInputChange("amount", Number(e.target.value))}
                required
                min="0"
                step="0.01"
                placeholder="0.00"
                helperText="Ingrese el valor en pesos colombianos (COP)"
                prefixIcon={
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                }
              />

              {/* Select mejorado con estilos modernos */}
              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">
                  País de la Transacción
                  <span className="text-danger-500 ml-1">*</span>
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 21v-4m0 0V5a2 2 0 012-2h6.5l1 1H21l-3 6 3 6h-8.5l-1-1H5a2 2 0 00-2 2zm9-13.5V9" />
                    </svg>
                  </div>
                  <select
                    value={formData.transactionCountry}
                    onChange={(e) => handleInputChange("transactionCountry", e.target.value)}
                    className="w-full pl-10 px-4 py-2.5 border border-gray-300 rounded-lg bg-white focus:ring-4 focus:ring-primary-100 focus:border-primary-500 outline-none transition-all hover:border-gray-400"
                    required
                  >
                    {COUNTRY_CODES.map((country) => (
                      <option key={country.code} value={country.code}>
                        {country.name} ({country.code})
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">
                  País del Usuario
                  <span className="text-danger-500 ml-1">*</span>
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                    </svg>
                  </div>
                  <select
                    value={formData.userCountry}
                    onChange={(e) => handleInputChange("userCountry", e.target.value)}
                    className="w-full pl-10 px-4 py-2.5 border border-gray-300 rounded-lg bg-white focus:ring-4 focus:ring-primary-100 focus:border-primary-500 outline-none transition-all hover:border-gray-400"
                    required
                  >
                    {COUNTRY_CODES.map((country) => (
                      <option key={country.code} value={country.code}>
                        {country.name} ({country.code})
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            </form>
          </CardContent>
          
          {/* Footer con botones */}
          <CardFooter>
            <div className="flex gap-3 w-full">
              {(result || error) && (
                <Button
                  type="button"
                  variant="ghost"
                  onClick={handleReset}
                  size="md"
                >
                  Limpiar
                </Button>
              )}
              <Button
                type="submit"
                onClick={handleSubmit}
                isLoading={isLoading}
                fullWidth
                size="md"
              >
                <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                Evaluar Transacción
              </Button>
            </div>
          </CardFooter>
        </Card>

        {/* Results Card - Modernizado */}
        {(result || error) && (
          <Card shadow="xl" hoverable className="animate-scale-up">
            <CardHeader>
              <CardTitle size="lg">
                {error ? "Error en la Evaluación" : "Resultado del Análisis"}
              </CardTitle>
            </CardHeader>
            <CardContent>
              {error ? (
                <div className="bg-gradient-to-br from-danger-50 to-danger-100 border-2 border-danger-300 rounded-xl p-6 animate-fade-in">
                  <div className="flex items-center gap-3">
                    <svg className="w-6 h-6 text-danger-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <p className="text-danger-800 font-semibold">{error}</p>
                  </div>
                </div>
              ) : result ? (
                <div className="space-y-6">
                  {/* Summary Section con gradiente */}
                  <div className="bg-gradient-to-br from-primary-50 via-secondary-50 to-primary-100 rounded-xl p-8 border-2 border-primary-200 shadow-inner">
                    <div className="text-center">
                      <p className="text-sm text-primary-700 font-bold mb-3 uppercase tracking-wide">
                        Estado de la Transacción
                      </p>
                      <Badge
                        variant={getSuspiciousVariant(result.suspicious)}
                        size="lg"
                        className="text-lg px-6 py-2.5 shadow-md"
                      >
                        {result.suspicious ? "⚠️ Sospechosa" : "✅ Normal"}
                      </Badge>
                    </div>
                  </div>

                  {/* Details Grid con iconos */}
                  <div className="space-y-5">
                    <div className="flex items-center justify-between pb-4 border-b-2 border-gray-100">
                      <div className="flex items-center gap-2">
                        <svg className="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <span className="text-sm font-semibold text-gray-600">
                          Monto Evaluado
                        </span>
                      </div>
                      <span className="text-lg font-bold text-gray-900">
                        {formatCurrency(formData.amount)}
                      </span>
                    </div>

                    <div className="flex items-center justify-between pb-4 border-b-2 border-gray-100">
                      <div className="flex items-center gap-2">
                        <svg className="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                        </svg>
                        <span className="text-sm font-semibold text-gray-600">
                          Nivel de Riesgo
                        </span>
                      </div>
                      <Badge variant={getRiskLevelVariant(result.riskLevel)} size="md">
                        {result.riskLevel}
                      </Badge>
                    </div>

                    <div>
                      <div className="flex items-center gap-2 mb-4">
                        <svg className="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                        </svg>
                        <span className="text-sm font-semibold text-gray-600">
                          Razones Detectadas
                        </span>
                      </div>
                      {result.reasons.length > 0 ? (
                        <ul className="space-y-3 bg-warning-50 rounded-lg p-4 border border-warning-200">
                          {result.reasons.map((reason, index) => (
                            <li
                              key={index}
                              className="flex items-start text-sm text-warning-800"
                            >
                              <svg
                                className="w-5 h-5 text-warning-600 mr-2 flex-shrink-0 mt-0.5"
                                fill="currentColor"
                                viewBox="0 0 20 20"
                              >
                                <path
                                  fillRule="evenodd"
                                  d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z"
                                  clipRule="evenodd"
                                />
                              </svg>
                              <span className="font-medium">{reason}</span>
                            </li>
                          ))}
                        </ul>
                      ) : (
                        <div className="bg-success-50 rounded-lg p-4 border border-success-200">
                          <p className="text-sm text-success-700 font-medium flex items-center gap-2">
                            <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                              <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                            </svg>
                            No se detectaron factores de riesgo
                          </p>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              ) : null}
            </CardContent>
          </Card>
        )}
      </div>
    </div>
  );
};

export default EvaluateTransaction;
