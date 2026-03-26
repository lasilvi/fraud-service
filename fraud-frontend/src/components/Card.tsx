import { type ReactNode } from "react";

interface CardProps {
  children: ReactNode;
  className?: string;
  padding?: "none" | "sm" | "md" | "lg";
  shadow?: "none" | "sm" | "md" | "lg";
}

const paddingStyles = {
  none: "",
  sm: "p-4",
  md: "p-6",
  lg: "p-8",
};

const shadowStyles = {
  none: "",
  sm: "shadow-sm",
  md: "shadow-md",
  lg: "shadow-lg",
};

export const Card = ({
  children,
  className = "",
  padding = "md",
  shadow = "md",
}: CardProps) => {
  const baseStyles = "bg-white rounded-lg border border-gray-200";
  const combinedClassName = `${baseStyles} ${paddingStyles[padding]} ${shadowStyles[shadow]} ${className}`.trim();

  return <div className={combinedClassName}>{children}</div>;
};

interface CardHeaderProps {
  children: ReactNode;
  className?: string;
}

export const CardHeader = ({ children, className = "" }: CardHeaderProps) => {
  return (
    <div className={`border-b border-gray-200 pb-4 mb-4 ${className}`.trim()}>
      {children}
    </div>
  );
};

interface CardTitleProps {
  children: ReactNode;
  className?: string;
}

export const CardTitle = ({ children, className = "" }: CardTitleProps) => {
  return (
    <h3 className={`text-xl font-bold text-gray-900 ${className}`.trim()}>
      {children}
    </h3>
  );
};

interface CardContentProps {
  children: ReactNode;
  className?: string;
}

export const CardContent = ({ children, className = "" }: CardContentProps) => {
  return <div className={className}>{children}</div>;
};
