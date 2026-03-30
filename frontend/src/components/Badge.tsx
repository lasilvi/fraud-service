import { type ReactNode } from "react";

/**
 * Badge moderno con múltiples variantes y tamaños
 * Diseño con colores vibrantes, bordes redondeados y animación opcional
 */

interface BadgeProps {
  children: ReactNode;
  variant: "primary" | "success" | "danger" | "warning" | "info" | "neutral" | "secondary";
  className?: string;
  size?: "sm" | "md" | "lg";
  dot?: boolean; // Muestra un punto indicador
  pill?: boolean; // Forma de píldora más redondeada
}

// Estilos mejorados con mejor contraste y animaciones sutiles
const variantStyles = {
  primary: "bg-primary-100 text-primary-800 border-primary-300 shadow-sm",
  secondary: "bg-secondary-100 text-secondary-800 border-secondary-300 shadow-sm",
  success: "bg-green-100 text-green-800 border-green-300 shadow-sm",
  danger: "bg-red-100 text-red-800 border-red-300 shadow-sm",
  warning: "bg-yellow-100 text-yellow-800 border-yellow-300 shadow-sm",
  info: "bg-blue-100 text-blue-800 border-blue-300 shadow-sm",
  neutral: "bg-gray-100 text-gray-800 border-gray-300 shadow-sm",
};

const sizeStyles = {
  sm: "px-2 py-0.5 text-xs",
  md: "px-2.5 py-1 text-sm",
  lg: "px-3 py-1.5 text-base",
};

// Colores para el dot indicator según variante
const dotColors = {
  primary: "bg-primary-600",
  secondary: "bg-secondary-600",
  success: "bg-green-600",
  danger: "bg-red-600",
  warning: "bg-yellow-600",
  info: "bg-blue-600",
  neutral: "bg-gray-600",
};

export const Badge = ({
  children,
  variant,
  className = "",
  size = "md",
  dot = false,
  pill = true,
}: BadgeProps) => {
  const baseStyles =
    "inline-flex items-center gap-1.5 font-semibold border transition-all duration-200 hover:shadow";
  
  // Bordes más o menos redondeados según pill
  const roundedStyle = pill ? "rounded-full" : "rounded-md";
  
  const combinedClassName = `${baseStyles} ${variantStyles[variant]} ${sizeStyles[size]} ${roundedStyle} ${className}`.trim();

  return (
    <span className={combinedClassName}>
      {/* Dot indicator opcional con animación pulse */}
      {dot && (
        <span
          className={`w-2 h-2 rounded-full ${dotColors[variant]} animate-pulse`}
          aria-hidden="true"
        />
      )}
      {children}
    </span>
  );
};
