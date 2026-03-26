import { type ReactNode } from "react";
import { Navbar } from "./Navbar";

/**
 * Layout principal con diseño moderno, navbar responsive y footer mejorado
 * Incluye fondo con gradiente sutil y espaciado optimizado
 */

interface LayoutProps {
  children: ReactNode;
}

export const Layout = ({ children }: LayoutProps) => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 via-white to-gray-50 flex flex-col">
      {/* Navbar fijo en la parte superior */}
      <Navbar />
      
      {/* Contenido principal con animación de entrada */}
      <main className="flex-1 py-8 px-4 sm:px-6 lg:py-12 lg:px-8 animate-fade-in">
        <div className="max-w-7xl mx-auto">
          {children}
        </div>
      </main>
      
      {/* Footer moderno con gradiente sutil */}
      <footer className="bg-gradient-to-r from-white via-gray-50 to-white border-t border-gray-200 mt-auto">
        <div className="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-6">
            {/* Información de la empresa */}
            <div>
              <h3 className="text-sm font-bold text-gray-900 mb-3 flex items-center gap-2">
                <div className="w-6 h-6 bg-gradient-to-br from-primary-600 to-primary-800 rounded-md flex items-center justify-center">
                  <svg className="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                  </svg>
                </div>
                Fraud Detection Service
              </h3>
              <p className="text-xs text-gray-600 leading-relaxed">
                Protección avanzada contra fraudes financieros con tecnología de última generación.
              </p>
            </div>
            
            {/* Links rápidos */}
            <div>
              <h3 className="text-sm font-bold text-gray-900 mb-3">Enlaces Rápidos</h3>
              <ul className="space-y-2 text-xs text-gray-600">
                <li>
                  <a href="#" className="hover:text-primary-600 transition-colors">
                    Documentación API
                  </a>
                </li>
                <li>
                  <a href="#" className="hover:text-primary-600 transition-colors">
                    Soporte Técnico
                  </a>
                </li>
                <li>
                  <a href="#" className="hover:text-primary-600 transition-colors">
                    Términos de Servicio
                  </a>
                </li>
              </ul>
            </div>
            
            {/* Tecnologías */}
            <div>
              <h3 className="text-sm font-bold text-gray-900 mb-3">Tecnologías</h3>
              <div className="flex flex-wrap gap-2">
                <span className="px-2 py-1 bg-primary-100 text-primary-700 text-xs font-medium rounded-md">
                  React 18
                </span>
                <span className="px-2 py-1 bg-green-100 text-green-700 text-xs font-medium rounded-md">
                  Spring Boot 3
                </span>
                <span className="px-2 py-1 bg-blue-100 text-blue-700 text-xs font-medium rounded-md">
                  PostgreSQL
                </span>
              </div>
            </div>
          </div>
          
          {/* Copyright */}
          <div className="pt-6 border-t border-gray-200">
            <p className="text-center text-gray-500 text-xs">
              © {new Date().getFullYear()} Fraud Detection Service. Todos los derechos reservados.
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
};
