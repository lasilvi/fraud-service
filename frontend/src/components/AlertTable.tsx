import {
  Table,
  TableHeader,
  TableBody,
  TableRow,
  TableHead,
  TableCell,
  Badge,
} from "./index";
import type { FraudAlert } from "../services/FraudService";
import { formatCurrency, formatDate, getRiskLevelVariant } from "../utils";

interface AlertTableProps {
  alerts: FraudAlert[];
}

export const AlertTable = ({ alerts }: AlertTableProps) => {
  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>ID Transacción</TableHead>
          <TableHead>Usuario</TableHead>
          <TableHead>Monto</TableHead>
          <TableHead>Nivel de Riesgo</TableHead>
          <TableHead>Razones</TableHead>
          <TableHead>Fecha</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {alerts.map((alert, index) => (
          <TableRow key={alert.id} striped index={index}>
            <TableCell>
              <span className="font-mono text-sm text-gray-700">
                {alert.transactionId}
              </span>
            </TableCell>
            <TableCell>
              <span className="font-medium text-gray-900">{alert.userId}</span>
            </TableCell>
            <TableCell>
              <span className="font-semibold">{formatCurrency(alert.amount)}</span>
            </TableCell>
            <TableCell>
              <Badge variant={getRiskLevelVariant(alert.riskLevel)} size="sm">
                {alert.riskLevel}
              </Badge>
            </TableCell>
            <TableCell>
              <div className="flex flex-wrap gap-1">
                {alert.reasons.map((reason) => (
                  <Badge key={reason} variant="neutral" size="sm">
                    {reason}
                  </Badge>
                ))}
              </div>
            </TableCell>
            <TableCell>
              <span className="text-sm text-gray-600">
                {formatDate(alert.timestamp)}
              </span>
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
};
