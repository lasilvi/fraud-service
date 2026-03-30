import { type ReactNode } from "react";

/**
 * Estado vacío moderno con ilustraciones atractivas y call-to-action
 * Ideal para listas vacías, resultados sin datos, o estados iniciales
 */

interface EmptyStateProps {
  title: string;
  description?: string;
  action?: {
    label: string;
    onClick: () => void;
    variant?: "primary" | "secondary";
  };
  icon?: "document" | "search" | "inbox" | "data" | "custom";
  customIcon?: ReactNode; // Permite pasar un icono personalizado
  size?: "sm" | "md" | "lg";
}

const iconSizes = {
  sm: "h-10 w-10",
  md: "h-16 w-16",
  lg: "h-24 w-24",
};

const textSizes = {
  sm: { title: "text-base", description: "text-xs" },
  md: { title: "text-lg", description: "text-sm" },
  lg: { title: "text-xl", description: "text-base" },
};

// Iconos SVG modernos y descriptivos
const icons = {
  document: (
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={1.5}
      d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
    />
  ),
  search: (
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={1.5}
      d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
    />
  ),
  inbox: (
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={1.5}
      d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"
    />
  ),
  data: (
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={1.5}
      d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"
    />
  ),
};

export const EmptyState = ({
  title,
  description,
  action,
  icon = "inbox",
  customIcon,
  size = "md",
}: EmptyStateProps) => {
  const actionVariant = action?.variant || "primary";
  
  const buttonStyles =
    actionVariant === "primary"
      ? "bg-gradient-to-r from-primary-600 to-primary-700 text-white hover:from-primary-700 hover:to-primary-800 shadow-md hover:shadow-lg"
      : "border-2 border-primary-500 text-primary-600 hover:bg-primary-50";

  return (
    <div className="text-center py-12 px-4 animate-fade-in">
      {/* Icono con gradiente sutil y animación */}
      <div className="flex justify-center mb-6">
        <div className="relative">
          {/* Círculo de fondo con gradiente */}
          <div className="absolute inset-0 bg-gradient-to-br from-primary-100 to-secondary-100 rounded-full blur-2xl opacity-50 animate-pulse" />
          
          {customIcon ? (
            <div className={`relative ${iconSizes[size]}`}>{customIcon}</div>
          ) : (
            <svg
              className={`${iconSizes[size]} text-gray-400 relative transition-transform hover:scale-110 duration-300`}
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              {icons[icon as keyof typeof icons]}
            </svg>
          )}
        </div>
      </div>
      
      {/* Título con gradiente */}
      <h3
        className={`${textSizes[size].title} font-bold text-gray-900 mb-2`}
      >
        {title}
      </h3>
      
      {/* Descripción */}
      {description && (
        <p
          className={`${textSizes[size].description} text-gray-500 leading-relaxed`}
        >
          {description}
        </p>
      )}
      
      {/* Botón de acción con hover mejorado */}
      {action && (
        <div className="mt-8">
          <button
            onClick={action.onClick}
            className={`inline-flex items-center gap-2 px-6 py-3 rounded-lg text-sm font-semibold transition-all duration-200 transform hover:scale-105 active:scale-95 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:ring-offset-2 ${buttonStyles}`}
          >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M12 4v16m8-8H4"
              />
            </svg>
            {action.label}
          </button>
        </div>
      )}
    </div>
  );
};
