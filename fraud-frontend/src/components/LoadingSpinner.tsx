/**
 * Spinner de carga moderno con múltiples variantes y tamaños
 * Incluye animaciones suaves y centrado automático
 */

interface LoadingSpinnerProps {
  size?: "sm" | "md" | "lg" | "xl";
  variant?: "primary" | "secondary" | "white";
  fullScreen?: boolean; // Cubre toda la pantalla
  message?: string; // Mensaje opcional de carga
}

const sizeStyles = {
  sm: "h-8 w-8 border-2",
  md: "h-12 w-12 border-3",
  lg: "h-16 w-16 border-4",
  xl: "h-24 w-24 border-[6px]",
};

const colorStyles = {
  primary: "border-primary-600 border-t-transparent",
  secondary: "border-secondary-600 border-t-transparent",
  white: "border-white border-t-transparent",
};

export const LoadingSpinner = ({
  size = "lg",
  variant = "primary",
  fullScreen = true,
  message,
}: LoadingSpinnerProps = {}) => {
  const containerClass = fullScreen
    ? "flex flex-col justify-center items-center min-h-screen gap-4"
    : "flex flex-col justify-center items-center gap-4 py-8";

  return (
    <div className={containerClass}>
      {/* Spinner con animación suave y gradiente sutil */}
      <div className="relative">
        {/* Anillo exterior con gradiente */}
        <div
          className={`animate-spin rounded-full ${sizeStyles[size]} ${colorStyles[variant]} shadow-lg`}
          style={{ animationDuration: "1s" }}
        />
        
        {/* Punto central pulsante */}
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
          <div
            className={`rounded-full bg-gradient-to-br from-primary-500 to-primary-700 animate-pulse`}
            style={{
              width: size === "sm" ? "8px" : size === "md" ? "12px" : size === "lg" ? "16px" : "20px",
              height: size === "sm" ? "8px" : size === "md" ? "12px" : size === "lg" ? "16px" : "20px",
            }}
          />
        </div>
      </div>
      
      {/* Mensaje de carga con animación */}
      {message && (
        <p className="text-gray-600 font-medium animate-pulse">
          {message}
        </p>
      )}
      
      {/* Puntos de carga animados */}
      {!message && (
        <div className="flex gap-1">
          <span className="w-2 h-2 bg-primary-600 rounded-full animate-bounce" style={{ animationDelay: "0ms" }} />
          <span className="w-2 h-2 bg-primary-600 rounded-full animate-bounce" style={{ animationDelay: "150ms" }} />
          <span className="w-2 h-2 bg-primary-600 rounded-full animate-bounce" style={{ animationDelay: "300ms" }} />
        </div>
      )}
    </div>
  );
};
