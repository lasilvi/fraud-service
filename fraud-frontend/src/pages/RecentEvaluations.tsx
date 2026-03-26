import { useEffect } from "react";
import {
  Card,
  CardContent,
  Table,
  TableHeader,
  TableBody,
  TableRow,
  TableHead,
  TableCell,
  Badge,
  PageHeader,
  LoadingSpinner,
  ErrorMessage,
  EmptyState,
} from "../components";
import { useEvaluationHistory } from "../hooks";
import {
  formatCurrency,
  formatDate,
  getRiskLevelVariant,
  getSuspiciousVariant,
} from "../utils";

const RecentEvaluations = () => {
  const { evaluations, isLoading, error, refetch } = useEvaluationHistory(10);

  useEffect(() => {
    refetch();
  }, []);

  if (isLoading) {
    return <LoadingSpinner message="Cargando historial..." />;
  }

  if (error) {
    return <ErrorMessage message={error} retry={refetch} variant="error" />;
  }

  // Cálculo de estadísticas
  const stats = {
    total: evaluations.length,
    suspicious: evaluations.filter((e) => e.suspicious).length,
    normal: evaluations.filter((e) => !e.suspicious).length,
    highRisk: evaluations.filter((e) => e.riskLevel === "HIGH").length,
  };

  return (
    <div className="animate-fade-in">
      <PageHeader
        title="Historial de Evaluaciones"
        subtitle="Consulta y analiza las transacciones evaluadas por el sistema de detección de fraudes"
        icon={
          <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        }
      />

      {evaluations.length === 0 ? (
        <EmptyState
          title="No hay evaluaciones recientes"
          description="Comienza evaluando tu primera transacción para verla aparecer aquí"
          icon="data"
          size="lg"
        />
      ) : (
        <div className="space-y-6">
          {/* Cards de Estadísticas */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 animate-slide-up">
            {/* Total */}
            <Card hoverable shadow="md" className="border-l-4 border-primary-500 hover:scale-105 transition-transform">
              <CardContent className="p-5">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-semibold text-gray-600 uppercase tracking-wide">
                      Total
                    </p>
                    <p className="text-3xl font-bold text-gray-900 mt-1">
                      {stats.total}
                    </p>
                  </div>
                  <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center">
                    <svg className="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                    </svg>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Sospechosas */}
            <Card hoverable shadow="md" className="border-l-4 border-danger-500 hover:scale-105 transition-transform">
              <CardContent className="p-5">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-semibold text-gray-600 uppercase tracking-wide">
                      Sospechosas
                    </p>
                    <p className="text-3xl font-bold text-danger-600 mt-1">
                      {stats.suspicious}
                    </p>
                  </div>
                  <div className="w-12 h-12 bg-danger-100 rounded-lg flex items-center justify-center">
                    <svg className="w-6 h-6 text-danger-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                    </svg>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Normales */}
            <Card hoverable shadow="md" className="border-l-4 border-success-500 hover:scale-105 transition-transform">
              <CardContent className="p-5">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-semibold text-gray-600 uppercase tracking-wide">
                      Normales
                    </p>
                    <p className="text-3xl font-bold text-success-600 mt-1">
                      {stats.normal}
                    </p>
                  </div>
                  <div className="w-12 h-12 bg-success-100 rounded-lg flex items-center justify-center">
                    <svg className="w-6 h-6 text-success-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Alto Riesgo */}
            <Card hoverable shadow="md" className="border-l-4 border-warning-500 hover:scale-105 transition-transform">
              <CardContent className="p-5">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-semibold text-gray-600 uppercase tracking-wide">
                      Alto Riesgo
                    </p>
                    <p className="text-3xl font-bold text-warning-600 mt-1">
                      {stats.highRisk}
                    </p>
                  </div>
                  <div className="w-12 h-12 bg-warning-100 rounded-lg flex items-center justify-center">
                    <svg className="w-6 h-6 text-warning-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                    </svg>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Tabla de Evaluaciones */}
          <Card padding="none" shadow="lg" className="animate-slide-up [animation-delay:150ms]">
            <Table>
              <TableHeader sticky>
                <TableRow>
                  <TableHead>Estado</TableHead>
                  <TableHead>Riesgo</TableHead>
                  <TableHead>Monto</TableHead>
                  <TableHead>Países</TableHead>
                  <TableHead>Razones</TableHead>
                  <TableHead>Fecha</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {evaluations.map((evaluation, index) => (
                  <TableRow key={index} striped index={index}>
                    <TableCell>
                      <Badge
                        variant={getSuspiciousVariant(evaluation.suspicious)}
                        dot
                        size="md"
                      >
                        {evaluation.suspicious ? "Sospechosa" : "Normal"}
                      </Badge>
                    </TableCell>
                    <TableCell>
                      <Badge variant={getRiskLevelVariant(evaluation.riskLevel)} size="md">
                        {evaluation.riskLevel}
                      </Badge>
                    </TableCell>
                    <TableCell className="font-bold text-gray-900">
                      {evaluation.amount
                        ? formatCurrency(evaluation.amount)
                        : "N/A"}
                    </TableCell>
                    <TableCell className="text-gray-600">
                      {evaluation.transactionCountry && evaluation.userCountry
                        ? (
                          <span className="inline-flex items-center gap-2 bg-gray-100 px-3 py-1 rounded-full">
                            <span className="font-medium">{evaluation.transactionCountry}</span>
                            <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7l5 5m0 0l-5 5m5-5H6" />
                            </svg>
                            <span className="font-medium">{evaluation.userCountry}</span>
                          </span>
                        )
                        : "N/A"}
                    </TableCell>
                    <TableCell>
                      {evaluation.reasons.length > 0 ? (
                        <div className="max-w-xs">
                          <details className="group">
                            <summary className="cursor-pointer text-xs text-primary-600 font-semibold hover:text-primary-700 flex items-center gap-1">
                              <span>{evaluation.reasons.length} razón{evaluation.reasons.length > 1 ? 'es' : ''}</span>
                              <svg className="w-4 h-4 transition-transform group-open:rotate-180" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                              </svg>
                            </summary>
                            <ul className="mt-2 space-y-1 text-xs text-gray-600 bg-gray-50 p-2 rounded-md">
                              {evaluation.reasons.map((reason, i) => (
                                <li key={i} className="flex items-start">
                                  <span className="text-warning-500 mr-1">•</span>
                                  <span>{reason}</span>
                                </li>
                              ))}
                            </ul>
                          </details>
                        </div>
                      ) : (
                        <span className="text-gray-400 text-sm italic">
                          Sin razones
                        </span>
                      )}
                    </TableCell>
                    <TableCell className="text-gray-600 text-sm">
                      {evaluation.evaluatedAt
                        ? formatDate(evaluation.evaluatedAt)
                        : "N/A"}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Card>
        </div>
      )}
    </div>
  );
};

export default RecentEvaluations;
