import { type ReactNode } from "react";

/**
 * Tabla moderna y responsive con scroll horizontal, hover effects y filas alternadas
 * Soporta sticky headers para mejor UX en tablas largas
 */

interface TableProps {
  children: ReactNode;
  className?: string;
  striped?: boolean; // Filas con colores alternados
  hoverable?: boolean; // Efecto hover en filas
  bordered?: boolean; // Bordes entre celdas
}

export const Table = ({
  children,
  className = "",
  bordered = false,
}: TableProps) => {
  return (
    // Container con scroll horizontal responsivo y sombras sutiles
    <div className="overflow-x-auto rounded-lg border border-gray-200 shadow-sm">
      <div className="inline-block min-w-full align-middle">
        <table
          className={`min-w-full divide-y divide-gray-300 ${bordered ? 'border-collapse' : ''} ${className}`.trim()}
        >
          {children}
        </table>
      </div>
    </div>
  );
};

interface TableHeaderProps {
  children: ReactNode;
  sticky?: boolean; // Header fijo al hacer scroll
}

export const TableHeader = ({ children, sticky = false }: TableHeaderProps) => {
  const stickyStyles = sticky ? "sticky top-0 z-10" : "";
  
  return (
    <thead className={`bg-gradient-to-r from-gray-50 to-gray-100 ${stickyStyles}`.trim()}>
      {children}
    </thead>
  );
};

interface TableBodyProps {
  children: ReactNode;
}

export const TableBody = ({ children }: TableBodyProps) => {
  return (
    <tbody className="bg-white divide-y divide-gray-200">
      {children}
    </tbody>
  );
};

interface TableRowProps {
  children: ReactNode;
  onClick?: () => void;
  className?: string;
  striped?: boolean;
  index?: number; // Usado para filas alternadas
}

export const TableRow = ({
  children,
  onClick,
  className = "",
  striped = false,
  index = 0,
}: TableRowProps) => {
  // Hover effect suave con elevación sutil
  const hoverStyles = onClick
    ? "cursor-pointer hover:bg-primary-50 hover:shadow-md transition-all duration-200"
    : "hover:bg-gray-50 transition-colors duration-150";
  
  // Colores alternados para mejor legibilidad
  const stripedStyles = striped && index % 2 === 1 ? "bg-gray-50" : "";
  
  return (
    <tr
      className={`${hoverStyles} ${stripedStyles} ${className}`.trim()}
      onClick={onClick}
    >
      {children}
    </tr>
  );
};

interface TableHeadProps {
  children: ReactNode;
  className?: string;
  sortable?: boolean; // Indica si la columna es ordenable
}

export const TableHead = ({
  children,
  className = "",
  sortable = false,
}: TableHeadProps) => {
  const sortableStyles = sortable
    ? "cursor-pointer hover:bg-gray-200 transition-colors select-none"
    : "";
  
  return (
    <th
      className={`px-6 py-4 text-left text-xs font-bold text-gray-700 uppercase tracking-wider ${sortableStyles} ${className}`.trim()}
    >
      <div className="flex items-center gap-2">
        {children}
        {sortable && (
          <svg
            className="w-4 h-4 text-gray-400"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M7 16V4m0 0L3 8m4-4l4 4m6 0v12m0 0l4-4m-4 4l-4-4"
            />
          </svg>
        )}
      </div>
    </th>
  );
};

interface TableCellProps {
  children: ReactNode;
  className?: string;
  align?: "left" | "center" | "right"; // Alineación del contenido
}

export const TableCell = ({
  children,
  className = "",
  align = "left",
}: TableCellProps) => {
  const alignStyles = {
    left: "text-left",
    center: "text-center",
    right: "text-right",
  };
  
  return (
    <td
      className={`px-6 py-4 text-sm text-gray-900 ${alignStyles[align]} ${className}`.trim()}
    >
      {children}
    </td>
  );
};
