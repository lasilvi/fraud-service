/**
 * Mensaje de error moderno con opciones de retry y diferentes variantes
 * Incluye iconos descriptivos y animaciones de entrada
 */

interface ErrorMessageProps {
  message: string;
  retry?: () => void;
  variant?: "error" | "warning" | "info";
  title?: string;
  fullWidth?: boolean;
}

const variantStyles = {
  error: {
    bg: "bg-danger-50",
    border: "border-danger-200",
    iconColor: "text-danger-600",
    titleColor: "text-danger-800",
    messageColor: "text-danger-700",
    btnColor: "text-danger-600 hover:text-danger-700 focus:ring-danger-500",
  },
  warning: {
    bg: "bg-warning-50",
    border: "border-warning-200",
    iconColor: "text-warning-600",
    titleColor: "text-warning-800",
    messageColor: "text-warning-700",
    btnColor: "text-warning-600 hover:text-warning-700 focus:ring-warning-500",
  },
  info: {
    bg: "bg-blue-50",
    border: "border-blue-200",
    iconColor: "text-blue-600",
    titleColor: "text-blue-800",
    messageColor: "text-blue-700",
    btnColor: "text-blue-600 hover:text-blue-700 focus:ring-blue-500",
  },
};

export const ErrorMessage = ({
  message,
  retry,
  variant = "error",
  title,
  fullWidth = false,
}: ErrorMessageProps) => {
  const styles = variantStyles[variant];
  const containerClass = fullWidth ? "w-full" : "max-w-2xl mx-auto";

  return (
    <div className={`${containerClass} p-6 animate-slide-down`}>
      <div
        className={`${styles.bg} ${styles.border} border rounded-xl p-6 shadow-md transition-all duration-300 hover:shadow-lg`}
      >
        <div className="flex items-start gap-4">
          {/* Icono descriptivo con animación */}
          <div className={`flex-shrink-0 ${styles.iconColor}`}>
            <svg
              className="w-7 h-7 animate-pulse"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              {variant === "error" && (
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              )}
              {variant === "warning" && (
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
                />
              )}
              {variant === "info" && (
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                />
              )}
            </svg>
          </div>
          
          {/* Contenido del mensaje */}
          <div className="flex-1 min-w-0">
            <h3 className={`text-base font-bold ${styles.titleColor}`}>
              {title || (variant === "error" ? "Ha ocurrido un error" : variant === "warning" ? "Advertencia" : "Información")}
            </h3>
            <p className={`mt-2 text-sm ${styles.messageColor} leading-relaxed`}>
              {message}
            </p>
            
            {/* Botón de retry con animación */}
            {retry && (
              <button
                onClick={retry}
                className={`mt-4 inline-flex items-center gap-2 text-sm font-semibold ${styles.btnColor} transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-md px-3 py-1.5 hover:scale-105 active:scale-95`}
              >
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
                  />
                </svg>
                Intentar nuevamente
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
