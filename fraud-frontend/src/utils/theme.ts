/**
 * Configuración global del theme para la aplicación
 * Define colores, tamaños, sombras y otros valores de diseño consistentes
 * Uso: import { COLORS, SPACING } from '@/utils/theme'
 */

// Paleta de colores principal
export const COLORS = {
  primary: {
    50: '#f5f3ff',
    100: '#ede9fe',
    200: '#ddd6fe',
    300: '#c4b5fd',
    400: '#a78bfa',
    500: '#8b5cf6',
    600: '#7c3aed',
    700: '#6d28d9',
    800: '#5b21b6',
    900: '#4c1d95',
  },
  secondary: {
    50: '#f0fdfa',
    100: '#ccfbf1',
    200: '#99f6e4',
    300: '#5eead4',
    400: '#2dd4bf',
    500: '#14b8a6',
    600: '#0d9488',
    700: '#0f766e',
    800: '#115e59',
    900: '#134e4a',
  },
  gray: {
    50: '#f9fafb',
    100: '#f3f4f6',
    200: '#e5e7eb',
    300: '#d1d5db',
    400: '#9ca3af',
    500: '#6b7280',
    600: '#4b5563',
    700: '#374151',
    800: '#1f2937',
    900: '#111827',
  },
  success: {
    light: '#86efac',
    main: '#22c55e',
    dark: '#15803d',
  },
  warning: {
    light: '#fcd34d',
    main: '#f59e0b',
    dark: '#b45309',
  },
  danger: {
    light: '#fca5a5',
    main: '#ef4444',
    dark: '#b91c1c',
  },
  info: {
    light: '#5eead4',
    main: '#14b8a6',
    dark: '#0f766e',
  },
} as const;

// Espaciado consistente (basado en sistema 4px)
export const SPACING = {
  xs: '0.25rem',   // 4px
  sm: '0.5rem',    // 8px
  md: '1rem',      // 16px
  lg: '1.5rem',    // 24px
  xl: '2rem',      // 32px
  '2xl': '3rem',   // 48px
  '3xl': '4rem',   // 64px
} as const;

// Tipografía
export const TYPOGRAPHY = {
  fontFamily: {
    sans: "'Inter', system-ui, -apple-system, sans-serif",
    heading: "'Poppins', 'Inter', system-ui, sans-serif",
    mono: "'JetBrains Mono', 'Fira Code', monospace",
  },
  fontSize: {
    xs: '0.75rem',    // 12px
    sm: '0.875rem',   // 14px
    base: '1rem',     // 16px
    lg: '1.125rem',   // 18px
    xl: '1.25rem',    // 20px
    '2xl': '1.5rem',  // 24px
    '3xl': '1.875rem',// 30px
    '4xl': '2.25rem', // 36px
    '5xl': '3rem',    // 48px
  },
  fontWeight: {
    light: 300,
    normal: 400,
    medium: 500,
    semibold: 600,
    bold: 700,
    extrabold: 800,
  },
  lineHeight: {
    tight: 1.25,
    normal: 1.5,
    relaxed: 1.75,
  },
} as const;

// Bordes redondeados
export const BORDER_RADIUS = {
  none: '0',
  sm: '0.25rem',   // 4px
  md: '0.5rem',    // 8px
  lg: '0.75rem',   // 12px
  xl: '1rem',      // 16px
  '2xl': '1.5rem', // 24px
  full: '9999px',
} as const;

// Sombras
export const SHADOWS = {
  none: 'none',
  sm: '0 1px 2px 0 rgba(0, 0, 0, 0.05)',
  md: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
  lg: '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
  xl: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
  '2xl': '0 25px 50px -12px rgba(0, 0, 0, 0.25)',
  inner: 'inset 0 2px 4px 0 rgba(0, 0, 0, 0.06)',
  glow: '0 0 20px rgba(139, 92, 246, 0.3)',
  'glow-lg': '0 0 30px rgba(139, 92, 246, 0.5)',
} as const;

// Duración de animaciones
export const TRANSITIONS = {
  fast: '150ms',
  base: '200ms',
  slow: '300ms',
  slower: '500ms',
} as const;

// Breakpoints responsive (alineados con Tailwind)
export const BREAKPOINTS = {
  sm: '640px',
  md: '768px',
  lg: '1024px',
  xl: '1280px',
  '2xl': '1536px',
} as const;

// Z-index layers para control de apilamiento
export const Z_INDEX = {
  dropdown: 1000,
  sticky: 1020,
  fixed: 1030,
  modalBackdrop: 1040,
  modal: 1050,
  popover: 1060,
  tooltip: 1070,
} as const;

// Variantes de componentes (usadas en Button, Badge, etc.)
export const COMPONENT_VARIANTS = {
  button: ['primary', 'secondary', 'danger', 'success', 'ghost', 'outline'] as const,
  badge: ['primary', 'secondary', 'danger', 'success', 'warning', 'info', 'gray'] as const,
  size: ['xs', 'sm', 'md', 'lg', 'xl'] as const,
} as const;

// Exportar tipos para TypeScript
export type ColorPalette = typeof COLORS;
export type SpacingScale = typeof SPACING;
export type ButtonVariant = typeof COMPONENT_VARIANTS.button[number];
export type BadgeVariant = typeof COMPONENT_VARIANTS.badge[number];
export type ComponentSize = typeof COMPONENT_VARIANTS.size[number];
