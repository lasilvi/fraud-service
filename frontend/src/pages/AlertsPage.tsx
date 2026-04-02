import { useEffect } from "react";
import {
  Card,
  CardContent,
  PageHeader,
  LoadingSpinner,
  ErrorMessage,
  EmptyState,
  AlertTable,
} from "../components";
import { useAlerts } from "../hooks";

const AlertsPage = () => {
  const { alerts, isLoading, error, refetch } = useAlerts();

  useEffect(() => {
    refetch();
  }, []);

  if (isLoading) {
    return (
      <div className="animate-fade-in">
        <PageHeader
          title="Alertas de Fraude"
          subtitle="Monitorea las alertas generadas por transacciones sospechosas en tiempo real"
          icon={
            <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
            </svg>
          }
        />
        <LoadingSpinner message="Cargando alertas..." />
      </div>
    );
  }

  if (error) {
    return (
      <div className="animate-fade-in">
        <PageHeader
          title="Alertas de Fraude"
          subtitle="Monitorea las alertas generadas por transacciones sospechosas en tiempo real"
          icon={
            <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
            </svg>
          }
        />
        <ErrorMessage message={error} retry={refetch} variant="error" />
      </div>
    );
  }

  return (
    <div className="animate-fade-in">
      <PageHeader
        title="Alertas de Fraude"
        subtitle="Monitorea las alertas generadas por transacciones sospechosas en tiempo real"
        icon={
          <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
          </svg>
        }
      />

      {alerts.length === 0 ? (
        <EmptyState
          title="No hay alertas de fraude"
          description="Las alertas aparecerán aquí cuando se detecten transacciones sospechosas"
          icon="inbox"
          size="lg"
        />
      ) : (
        <Card shadow="lg">
          <CardContent>
            <AlertTable alerts={alerts} />
          </CardContent>
        </Card>
      )}
    </div>
  );
};

export default AlertsPage;
