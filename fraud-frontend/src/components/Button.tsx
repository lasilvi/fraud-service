import { type ButtonHTMLAttributes, type ReactNode } from "react";

/**
 * Botón moderno y accesible con múltiples variantes y estados
 * Incluye animaciones suaves, hover effects y estado de carga
 */

type ButtonVariant = "primary" | "secondary" | "danger" | "success" | "ghost" | "outline";
type ButtonSize = "xs" | "sm" | "md" | "lg" | "xl";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
  children: ReactNode;
  isLoading?: boolean;
  fullWidth?: boolean;
}

// Estilos modernos con gradientes sutiles y sombras
const variantStyles: Record<ButtonVariant, string> = {
  primary:
    "bg-gradient-to-r from-primary-600 to-primary-700 text-white shadow-md hover:shadow-lg hover:from-primary-700 hover:to-primary-800 focus:ring-primary-500 active:scale-95",
  secondary:
    "bg-gradient-to-r from-secondary-500 to-secondary-600 text-white shadow-md hover:shadow-lg hover:from-secondary-600 hover:to-secondary-700 focus:ring-secondary-500 active:scale-95",
  danger:
    "bg-gradient-to-r from-danger-500 to-danger-600 text-white shadow-md hover:shadow-lg hover:from-danger-600 hover:to-danger-700 focus:ring-danger-500 active:scale-95",
  success:
    "bg-gradient-to-r from-success-500 to-success-600 text-white shadow-md hover:shadow-lg hover:from-success-600 hover:to-success-700 focus:ring-success-500 active:scale-95",
  ghost:
    "text-gray-700 hover:bg-gray-100 hover:text-gray-900 focus:ring-gray-300 active:bg-gray-200",
  outline:
    "border-2 border-primary-500 text-primary-600 hover:bg-primary-50 hover:border-primary-600 focus:ring-primary-500 active:bg-primary-100",
};

const sizeStyles: Record<ButtonSize, string> = {
  xs: "px-2.5 py-1.5 text-xs font-medium",
  sm: "px-3 py-2 text-sm font-medium",
  md: "px-4 py-2.5 text-base font-semibold",
  lg: "px-6 py-3 text-lg font-semibold",
  xl: "px-8 py-4 text-xl font-bold",
};

export const Button = ({
  variant = "primary",
  size = "md",
  children,
  isLoading = false,
  fullWidth = false,
  disabled,
  className = "",
  ...props
}: ButtonProps) => {
  // Estilos base con transiciones suaves y animaciones
  const baseStyles =
    "inline-flex items-center justify-center rounded-lg focus:outline-none focus:ring-2 focus:ring-offset-2 disabled:opacity-60 disabled:cursor-not-allowed disabled:transform-none transition-all duration-200 ease-in-out transform";

  const widthStyle = fullWidth ? "w-full" : "";

  const combinedClassName = `${baseStyles} ${variantStyles[variant]} ${sizeStyles[size]} ${widthStyle} ${className}`.trim();

  return (
    <button
      className={combinedClassName}
      disabled={disabled || isLoading}
      {...props}
    >
      {isLoading ? (
        <div className="flex items-center justify-center gap-2">
          {/* Spinner mejorado con animación suave */}
          <svg
            className="animate-spin h-5 w-5"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
          >
            <circle
              className="opacity-25"
              cx="12"
              cy="12"
              r="10"
              stroke="currentColor"
              strokeWidth="4"
            />
            <path
              className="opacity-75"
              fill="currentColor"
              d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
            />
          </svg>
          <span>Cargando...</span>
        </div>
      ) : (
        children
      )}
    </button>
  );
};
