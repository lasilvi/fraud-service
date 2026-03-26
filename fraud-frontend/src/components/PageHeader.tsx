import { type ReactNode } from "react";

/**
 * Header de página moderno con soporte para breadcrumbs y acciones
 * Incluye animaciones de entrada y diseño responsivo
 */

interface Breadcrumb {
  label: string;
  href?: string;
}

interface PageHeaderProps {
  title: string;
  subtitle?: string;
  action?: ReactNode;
  breadcrumbs?: Breadcrumb[];
  icon?: ReactNode;
}

export const PageHeader = ({
  title,
  subtitle,
  action,
  breadcrumbs,
  icon,
}: PageHeaderProps) => {
  return (
    <div className="mb-8 animate-slide-down">
      {/* Breadcrumbs opcionales */}
      {breadcrumbs && breadcrumbs.length > 0 && (
        <nav className="flex mb-4" aria-label="Breadcrumb">
          <ol className="inline-flex items-center space-x-1 md:space-x-3">
            {breadcrumbs.map((crumb, index) => (
              <li key={index} className="inline-flex items-center">
                {index > 0 && (
                  <svg
                    className="w-4 h-4 text-gray-400 mx-1"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      fillRule="evenodd"
                      d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                      clipRule="evenodd"
                    />
                  </svg>
                )}
                {crumb.href ? (
                  <a
                    href={crumb.href}
                    className="text-sm font-medium text-gray-700 hover:text-primary-600 transition-colors"
                  >
                    {crumb.label}
                  </a>
                ) : (
                  <span className="text-sm font-medium text-gray-500">
                    {crumb.label}
                  </span>
                )}
              </li>
            ))}
          </ol>
        </nav>
      )}
      
      {/* Header principal */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div className="flex items-start gap-4">
          {/* Icono opcional */}
          {icon && (
            <div className="flex-shrink-0 w-12 h-12 bg-gradient-to-br from-primary-100 to-secondary-100 rounded-xl flex items-center justify-center text-primary-600">
              {icon}
            </div>
          )}
          
          {/* Título y subtítulo */}
          <div>
            <h1 className="text-3xl sm:text-4xl font-extrabold text-gray-900 font-heading tracking-tight">
              {title}
            </h1>
            {subtitle && (
              <p className="mt-2 text-base text-gray-600 max-w-2xl leading-relaxed">
                {subtitle}
              </p>
            )}
          </div>
        </div>
        
        {/* Acción opcional (botones, etc.) */}
        {action && (
          <div className="flex-shrink-0">
            {action}
          </div>
        )}
      </div>
      
      {/* Línea decorativa con gradiente */}
      <div className="mt-6 h-1 w-full bg-gradient-to-r from-primary-500 via-secondary-500 to-primary-500 rounded-full" />
    </div>
  );
};
