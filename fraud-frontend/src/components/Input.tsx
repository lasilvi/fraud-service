import { type InputHTMLAttributes, type ReactNode } from "react";

/**
 * Input moderno con validación visual, animaciones suaves y soporte para iconos
 * Incluye estados de error, disabled, y helper text descriptivo
 */

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  helperText?: string;
  prefixIcon?: ReactNode; // Icono al inicio del input
  suffixIcon?: ReactNode; // Icono al final del input
  inputSize?: "sm" | "md" | "lg";
}

const sizeStyles = {
  sm: "px-3 py-1.5 text-sm",
  md: "px-4 py-2.5 text-base",
  lg: "px-5 py-3 text-lg",
};

export const Input = ({
  label,
  error,
  helperText,
  prefixIcon,
  suffixIcon,
  inputSize = "md",
  className = "",
  disabled,
  ...props
}: InputProps) => {
  // Estilos base con transiciones suaves y mejor UX
  const baseInputStyles =
    "w-full rounded-lg border bg-white focus:outline-none transition-all duration-200 placeholder:text-gray-400";
  
  // Estilos condicionales según estado
  const stateStyles = error
    ? "border-danger-300 focus:border-danger-500 focus:ring-4 focus:ring-danger-100"
    : "border-gray-300 focus:border-primary-500 focus:ring-4 focus:ring-primary-100";
  
  const disabledStyles = disabled
    ? "bg-gray-100 cursor-not-allowed opacity-60"
    : "hover:border-gray-400";
  
  // Ajustar padding si hay iconos
  const paddingStyle = prefixIcon
    ? "pl-10"
    : suffixIcon
    ? "pr-10"
    : sizeStyles[inputSize];
  
  const inputClassName = `${baseInputStyles} ${stateStyles} ${disabledStyles} ${paddingStyle} ${className}`.trim();

  return (
    <div className="w-full">
      {/* Label con animación sutil */}
      {label && (
        <label className="block text-sm font-semibold text-gray-700 mb-2 transition-colors">
          {label}
          {props.required && <span className="text-danger-500 ml-1">*</span>}
        </label>
      )}
      
      {/* Container para input con iconos */}
      <div className="relative">
        {/* Icono prefijo */}
        {prefixIcon && (
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400">
            {prefixIcon}
          </div>
        )}
        
        {/* Input principal */}
        <input
          className={inputClassName}
          disabled={disabled}
          aria-invalid={error ? "true" : "false"}
          aria-describedby={error ? `${props.id}-error` : helperText ? `${props.id}-helper` : undefined}
          {...props}
        />
        
        {/* Icono sufijo */}
        {suffixIcon && (
          <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none text-gray-400">
            {suffixIcon}
          </div>
        )}
        
        {/* Indicador de error visual */}
        {error && (
          <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
            <svg
              className="h-5 w-5 text-danger-500"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                fillRule="evenodd"
                d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
                clipRule="evenodd"
              />
            </svg>
          </div>
        )}
      </div>
      
      {/* Mensaje de error con animación */}
      {error && (
        <p
          id={`${props.id}-error`}
          className="mt-2 text-sm text-danger-600 animate-slide-down flex items-center gap-1"
        >
          <svg className="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
            <path
              fillRule="evenodd"
              d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
              clipRule="evenodd"
            />
          </svg>
          {error}
        </p>
      )}
      
      {/* Helper text */}
      {helperText && !error && (
        <p
          id={`${props.id}-helper`}
          className="mt-2 text-sm text-gray-500"
        >
          {helperText}
        </p>
      )}
    </div>
  );
};
