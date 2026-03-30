import { type ReactNode } from "react";

/**
 * Card moderno con hover effects, sombras suaves y bordes redondeados
 * Componible con Header, Title y Content para flexibilidad máxima
 */

interface CardProps {
  children: ReactNode;
  className?: string;
  padding?: "none" | "sm" | "md" | "lg" | "xl";
  shadow?: "none" | "sm" | "md" | "lg" | "xl";
  hoverable?: boolean; // Añade efecto hover
  bordered?: boolean;
}

const paddingStyles = {
  none: "",
  sm: "p-4",
  md: "p-6",
  lg: "p-8",
  xl: "p-10",
};

const shadowStyles = {
  none: "",
  sm: "shadow-sm",
  md: "shadow-md",
  lg: "shadow-lg",
  xl: "shadow-xl",
};

export const Card = ({
  children,
  className = "",
  padding = "md",
  shadow = "md",
  hoverable = false,
  bordered = true,
}: CardProps) => {
  // Estilos base mejorados con transiciones suaves
  const baseStyles = "bg-white rounded-xl transition-all duration-300 ease-in-out";
  const borderStyle = bordered ? "border border-gray-200" : "";
  
  // Efecto hover elegante con elevación y shadow incrementado
  const hoverStyle = hoverable
    ? "hover:shadow-xl hover:-translate-y-1 hover:border-primary-200 cursor-pointer"
    : "";

  const combinedClassName = `${baseStyles} ${borderStyle} ${paddingStyles[padding]} ${shadowStyles[shadow]} ${hoverStyle} ${className}`.trim();

  return <div className={combinedClassName}>{children}</div>;
};

interface CardHeaderProps {
  children: ReactNode;
  className?: string;
  divider?: boolean; // Controla si muestra borde inferior
}

export const CardHeader = ({
  children,
  className = "",
  divider = true,
}: CardHeaderProps) => {
  const dividerStyle = divider ? "border-b border-gray-200 pb-4 mb-4" : "mb-4";
  
  return (
    <div className={`${dividerStyle} ${className}`.trim()}>
      {children}
    </div>
  );
};

interface CardTitleProps {
  children: ReactNode;
  className?: string;
  size?: "sm" | "md" | "lg" | "xl";
}

const titleSizeStyles = {
  sm: "text-lg font-semibold",
  md: "text-xl font-bold",
  lg: "text-2xl font-bold",
  xl: "text-3xl font-extrabold",
};

export const CardTitle = ({
  children,
  className = "",
  size = "md",
}: CardTitleProps) => {
  return (
    <h3
      className={`${titleSizeStyles[size]} text-gray-900 font-heading ${className}`.trim()}
    >
      {children}
    </h3>
  );
};

interface CardContentProps {
  children: ReactNode;
  className?: string;
}

export const CardContent = ({ children, className = "" }: CardContentProps) => {
  return <div className={`text-gray-700 ${className}`.trim()}>{children}</div>;
};

interface CardFooterProps {
  children: ReactNode;
  className?: string;
  divider?: boolean;
}

// Nuevo componente CardFooter para acciones y botones
export const CardFooter = ({
  children,
  className = "",
  divider = true,
}: CardFooterProps) => {
  const dividerStyle = divider ? "border-t border-gray-200 pt-4 mt-4" : "mt-4";
  
  return (
    <div className={`${dividerStyle} flex items-center justify-end gap-2 ${className}`.trim()}>
      {children}
    </div>
  );
};
