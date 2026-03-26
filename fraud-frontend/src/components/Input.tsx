import { type InputHTMLAttributes } from "react";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  helperText?: string;
}

export const Input = ({
  label,
  error,
  helperText,
  className = "",
  ...props
}: InputProps) => {
  const baseInputStyles =
    "w-full px-4 py-2 border rounded-md focus:ring-2 focus:border-transparent outline-none transition";
  const errorStyles = error
    ? "border-red-300 focus:ring-red-500"
    : "border-gray-300 focus:ring-purple-500";
  const inputClassName = `${baseInputStyles} ${errorStyles} ${className}`.trim();

  return (
    <div className="w-full">
      {label && (
        <label className="block text-sm font-medium text-gray-700 mb-2">
          {label}
        </label>
      )}
      <input className={inputClassName} {...props} />
      {error && <p className="mt-1 text-sm text-red-600">{error}</p>}
      {helperText && !error && (
        <p className="mt-1 text-sm text-gray-500">{helperText}</p>
      )}
    </div>
  );
};
