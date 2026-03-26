# 🎨 Sistema de Diseño - Fraud Detection Frontend

## 📚 Tabla de Contenidos
- [Introducción](#introducción)
- [Paleta de Colores](#paleta-de-colores)
- [Tipografía](#tipografía)
- [Componentes](#componentes)
- [Animaciones](#animaciones)
- [Responsive Design](#responsive-design)
- [Uso y Ejemplos](#uso-y-ejemplos)

---

## 🎯 Introducción

Sistema de diseño moderno implementado con **TailwindCSS** siguiendo principios de arquitectura limpia y SOLID. Diseñado para ser consistente, accesible y completamente responsive.

### Características Principales
✅ Diseño moderno y profesional  
✅ Totalmente responsive (mobile, tablet, desktop)  
✅ Animaciones y microinteracciones suaves  
✅ Sistema de colores semántico  
✅ Componentes reutilizables  
✅ Dark mode ready  
✅ Accesibilidad (ARIA labels)  

---

## 🎨 Paleta de Colores

### Colores Principales

#### Primary (Violeta)
- `50`: #faf5ff - Backgrounds muy claros
- `100`: #f3e8ff - Highlights
- `200`: #e9d5ff - Borders suaves
- `300`: #d8b4fe - Estados hover
- `400`: #c084fc
- `500`: #a855f7 - Focus states
- `600`: **#8b5cf6** - Color principal (botones, enlaces)
- `700`: #7c3aed - Estados activos
- `800`: #6d28d9
- `900`: #5b21b6 - Textos importantes

#### Secondary (Turquesa)
- `50`: #f0fdfa
- `100`: #ccfbf1
- `200`: #99f6e4
- `300`: #5eead4
- `400`: #2dd4bf
- `500`: **#14b8a6** - Color secundario
- `600`: #0d9488
- `700`: #0f766e
- `800`: #115e59
- `900`: #134e4a

#### Danger (Rojo)
- `600`: **#dc2626** - Errores, alertas

#### Success (Verde)
- `600`: **#16a34a** - Éxito, confirmaciones

#### Warning (Amarillo)
- `500`: **#eab308** - Advertencias

### Colores Neutrales
- `gray-50` a `gray-900`: Escala de grises para textos y backgrounds

---

## ✍️ Tipografía

### Fuentes

```css
/* Sans-serif principal */
font-family: 'Inter', sans-serif;

/* Títulos */
font-family: 'Poppins', sans-serif;

/* Código */
font-family: 'JetBrains Mono', monospace;
```

### Escalas de Tamaño

| Clase | Tamaño | Uso |
|-------|--------|-----|
| `text-xs` | 0.75rem | Textos muy pequeños, labels |
| `text-sm` | 0.875rem | Textos secundarios |
| `text-base` | 1rem | Texto normal |
| `text-lg` | 1.125rem | Destacados |
| `text-xl` | 1.25rem | Subtítulos |
| `text-2xl` | 1.5rem | Títulos H3 |
| `text-3xl` | 1.875rem | Títulos H2 |
| `text-4xl` | 2.25rem | Títulos H1 |
| `text-5xl` | 3rem | Títulos hero |

### Pesos

- `font-light`: 300
- `font-normal`: 400
- `font-medium`: 500
- `font-semibold`: 600
- `font-bold`: 700
- `font-extrabold`: 800

---

## 🧩 Componentes

### Button

Botón versátil con múltiples variantes y tamaños.

#### Variantes
- `primary`: Violeta gradiente (acción principal)
- `secondary`: Turquesa (acción secundaria)
- `danger`: Rojo (acciones destructivas)
- `success`: Verde (confirmaciones)
- `warning`: Amarillo (advertencias)
- `ghost`: Transparente con borde
- `outline`: Borde sólido sin relleno

#### Tamaños
- `xs`: Extra pequeño (px-2 py-1, text-xs)
- `sm`: Pequeño (px-3 py-1.5, text-sm)
- `md`: Mediano - default (px-4 py-2, text-base)
- `lg`: Grande (px-6 py-3, text-lg)
- `xl`: Extra grande (px-8 py-4, text-xl)

#### Props
```typescript
interface ButtonProps {
  variant?: 'primary' | 'secondary' | 'danger' | 'success' | 'warning' | 'ghost' | 'outline';
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
  fullWidth?: boolean;
  isLoading?: boolean;
  children: ReactNode;
  // ... standard button props
}
```

#### Ejemplo
```tsx
<Button variant="primary" size="lg" fullWidth>
  Evaluar Transacción
</Button>
```

---

### Card

Contenedor elegante con sombras y hover effects.

#### Props
```typescript
interface CardProps {
  shadow?: 'sm' | 'md' | 'lg' | 'xl' | '2xl';
  padding?: 'none' | 'sm' | 'md' | 'lg';
  hoverable?: boolean;
  bordered?: boolean;
  children: ReactNode;
}
```

#### Subcomponentes
- `CardHeader`: Encabezado de la tarjeta
- `CardTitle`: Título (sizes: sm, md, lg)
- `CardContent`: Contenido principal
- `CardFooter`: Pie de tarjeta (acciones)

#### Ejemplo
```tsx
<Card shadow="lg" hoverable>
  <CardHeader>
    <CardTitle size="lg">Título</CardTitle>
  </CardHeader>
  <CardContent>
    Contenido...
  </CardContent>
  <CardFooter>
    <Button>Acción</Button>
  </CardFooter>
</Card>
```

---

### Badge

Indicador de estado compacto.

#### Variantes
- `primary`: Violeta
- `secondary`: Turquesa
- `danger`: Rojo
- `success`: Verde
- `warning`: Amarillo
- `info`: Azul
- `default`: Gris

#### Props
```typescript
interface BadgeProps {
  variant?: 'primary' | 'secondary' | 'danger' | 'success' | 'warning' | 'info' | 'default';
  size?: 'sm' | 'md' | 'lg';
  dot?: boolean;      // Indicador pulsante
  pill?: boolean;     // Bordes redondeados
  children: ReactNode;
}
```

#### Ejemplo
```tsx
<Badge variant="danger" dot size="md">
  Sospechosa
</Badge>
```

---

### Input

Campo de texto con validación y soporte para iconos.

#### Props
```typescript
interface InputProps {
  label?: string;
  error?: string;
  helperText?: string;
  prefixIcon?: ReactNode;
  suffixIcon?: ReactNode;
  inputSize?: 'sm' | 'md' | 'lg';
  // ... standard input props
}
```

#### Ejemplo
```tsx
<Input
  label="Monto"
  type="number"
  prefixIcon={<DollarIcon />}
  helperText="Ingrese el valor en COP"
  error={errors.amount}
/>
```

---

### Table

Tabla moderna con headers sticky y filas rayadas.

#### Props
```typescript
interface TableProps {
  striped?: boolean;    // Filas alternadas
  hoverable?: boolean;  // Efecto hover
  bordered?: boolean;   // Bordes visibles
}

interface TableHeaderProps {
  sticky?: boolean;     // Header fijo
}

interface TableRowProps {
  striped?: boolean;
  index?: number;       // Para filas rayadas
}

interface TableCellProps {
  align?: 'left' | 'center' | 'right';
}
```

#### Ejemplo
```tsx
<Table striped hoverable>
  <TableHeader sticky>
    <TableRow>
      <TableHead>Estado</TableHead>
      <TableHead align="right">Monto</TableHead>
    </TableRow>
  </TableHeader>
  <TableBody>
    {data.map((item, i) => (
      <TableRow key={i} index={i}>
        <TableCell>{item.status}</TableCell>
        <TableCell align="right">{item.amount}</TableCell>
      </TableRow>
    ))}
  </TableBody>
</Table>
```

---

### Estados

#### LoadingSpinner
```typescript
interface LoadingSpinnerProps {
  size?: 'sm' | 'md' | 'lg' | 'xl';
  variant?: 'primary' | 'secondary' | 'white';
  fullScreen?: boolean;
  message?: string;
}
```

#### ErrorMessage
```typescript
interface ErrorMessageProps {
  message: string;
  title?: string;
  variant?: 'error' | 'warning' | 'info';
  retry?: () => void;
  fullWidth?: boolean;
}
```

#### EmptyState
```typescript
interface EmptyStateProps {
  title: string;
  description: string;
  icon?: 'document' | 'search' | 'inbox' | 'data';
  customIcon?: ReactNode;
  size?: 'sm' | 'md' | 'lg';
  action?: ReactNode;
}
```

---

## ✨ Animaciones

### Animaciones Disponibles

| Clase | Efecto | Duración | Uso |
|-------|--------|----------|-----|
| `animate-fade-in` | Aparición gradual | 0.3s | Contenedores principales |
| `animate-fade-out` | Desaparición gradual | 0.3s | Modales, notificaciones |
| `animate-slide-up` | Deslizar hacia arriba | 0.3s | Cards, formularios |
| `animate-slide-down` | Deslizar hacia abajo | 0.3s | Dropdowns, acordeones |
| `animate-scale-up` | Escalar desde centro | 0.2s | Botones, modales |
| `animate-pulse-slow` | Pulso suave | 3s loop | Indicadores, badges dot |
| `animate-spin-slow` | Rotación lenta | 2s loop | Loading spinners |

### Transiciones

```css
/* Suave */
transition-all-smooth: transition-all duration-200 ease-in-out

/* Standard */
transition-transform
transition-colors
transition-shadow

/* Duraciones */
duration-150  /* 150ms - Muy rápido */
duration-200  /* 200ms - Rápido (default) */
duration-300  /* 300ms - Normal */
duration-500  /* 500ms - Lento */
```

### Efectos Hover Comunes

```css
/* Elevación */
hover:-translate-y-1 hover:shadow-xl

/* Escala */
hover:scale-105 transition-transform

/* Brillo */
hover:brightness-110

/* Estado activo */
active:scale-95
```

### Delays para Stagger

Usar la sintaxis arbitraria de Tailwind:

```tsx
<div className="animate-slide-up [animation-delay:100ms]">...</div>
<div className="animate-slide-up [animation-delay:200ms]">...</div>
<div className="animate-slide-up [animation-delay:300ms]">...</div>
```

---

## 📱 Responsive Design

### Breakpoints

| Breakpoint | Min Width | Device |
|------------|-----------|--------|
| `sm` | 640px | Mobile landscape / Small tablet |
| `md` | 768px | Tablet |
| `lg` | 1024px | Desktop |
| `xl` | 1280px | Large desktop |
| `2xl` | 1536px | Extra large |

### Patrones Comunes

#### Grid Responsivo
```tsx
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
  {/* 1 columna mobile, 2 tablet, 4 desktop */}
</div>
```

#### Padding/Margin Adaptativo
```tsx
<div className="p-4 md:p-6 lg:p-8">
  {/* 16px mobile, 24px tablet, 32px desktop */}
</div>
```

#### Texto Responsivo
```tsx
<h1 className="text-2xl md:text-3xl lg:text-4xl">
  Título Adaptativo
</h1>
```

#### Hidden/Visible
```tsx
{/* Solo mobile */}
<div className="block lg:hidden">Mobile menu</div>

{/* Solo desktop */}
<div className="hidden lg:block">Desktop nav</div>
```

### Mobile-First

Todos los estilos base son mobile, y se adaptan hacia arriba:

```tsx
// ❌ Desktop-first (evitar)
<div className="w-full lg:w-1/2 md:w-3/4">

// ✅ Mobile-first (correcto)
<div className="w-full md:w-3/4 lg:w-1/2">
```

---

## 🚀 Uso y Ejemplos

### Estructura de Página Típica

```tsx
import { 
  PageHeader, 
  Card, 
  Button, 
  Input,
  Badge 
} from '../components';

const MyPage = () => {
  return (
    <div className="max-w-6xl mx-auto animate-fade-in">
      {/* Header */}
      <PageHeader
        title="Mi Página"
        subtitle="Descripción de la funcionalidad"
        icon={<MyIcon />}
      />

      {/* Contenido */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Card con formulario */}
        <Card shadow="lg" className="animate-slide-up">
          <CardHeader>
            <CardTitle>Formulario</CardTitle>
          </CardHeader>
          <CardContent>
            <form className="space-y-4">
              <Input
                label="Campo"
                placeholder="Valor"
                prefixIcon={<Icon />}
              />
              {/* ... más campos */}
            </form>
          </CardContent>
          <CardFooter>
            <Button variant="primary" fullWidth>
              Enviar
            </Button>
          </CardFooter>
        </Card>

        {/* Card con resultados */}
        <Card shadow="lg" className="animate-slide-up [animation-delay:150ms]">
          <CardHeader>
            <CardTitle>Resultados</CardTitle>
          </CardHeader>
          <CardContent>
            <Badge variant="success" dot>
              Procesado
            </Badge>
            {/* ... contenido */}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};
```

### Dashboard con Estadísticas

```tsx
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 animate-slide-up">
  {stats.map((stat, i) => (
    <Card 
      key={i}
      hoverable 
      shadow="md" 
      className="border-l-4 border-primary-500 hover:scale-105 transition-transform"
    >
      <CardContent className="p-5">
        <div className="flex items-center justify-between">
          <div>
            <p className="text-sm font-semibold text-gray-600 uppercase">
              {stat.label}
            </p>
            <p className="text-3xl font-bold text-gray-900 mt-1">
              {stat.value}
            </p>
          </div>
          <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center">
            {stat.icon}
          </div>
        </div>
      </CardContent>
    </Card>
  ))}
</div>
```

### Formulario con Validación

```tsx
const [errors, setErrors] = useState<Record<string, string>>({});

<Input
  label="Email"
  type="email"
  value={email}
  onChange={(e) => setEmail(e.target.value)}
  error={errors.email}
  helperText="Ingresa un email válido"
  prefixIcon={
    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207" />
    </svg>
  }
/>
```

---

## 🎨 Utilidades Personalizadas

### Gradientes

```css
/* Backgrounds */
.bg-gradient-primary     /* Violeta */
.bg-gradient-secondary   /* Turquesa */
.bg-gradient-danger      /* Rojo */
.bg-gradient-radial      /* Radial */

/* Texto */
.text-gradient           /* Texto con gradiente */
```

### Efectos Glass

```css
.glass {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}
```

### Sombras Especiales

```css
shadow-glow      /* Brillo violeta suave */
shadow-glow-lg   /* Brillo violeta intenso */
```

---

## 📖 Mejores Prácticas

### ✅ DO

- Usar componentes reutilizables en lugar de duplicar código
- Aplicar animaciones sutiles para mejor UX
- Mantener consistencia en espaciados y tamaños
- Mobile-first para responsive design
- Usar variantes semánticas (success, danger, warning)
- Agregar aria-labels para accesibilidad

### ❌ DON'T

- No usar colores hardcodeados, usar el sistema de colores
- No mezclar unidades (usar rem/em o px consistentemente)
- No crear estilos inline sin necesidad
- No olvidar el estado hover/focus/active
- No ignorar el responsive design

---

## 🔧 Configuración Técnica

### TailwindCSS Config

El sistema está configurado en `tailwind.config.js`:

- Colores extendidos (primary, secondary, danger, success, warning)
- Fuentes personalizadas (Inter, Poppins, JetBrains Mono)
- Animaciones y keyframes
- Sombras personalizadas
- Border radius extendido

### CSS Global

Estilos base en `src/index.css`:

- Variables CSS para theming
- Reset y normalize
- Scrollbar personalizado
- Utilidades globales
- Soporte dark mode

### Theme Utility

Constantes centralizadas en `src/utils/theme.ts`:

```typescript
import { COLORS, SPACING, TYPOGRAPHY, SHADOWS } from '@/utils/theme';

// Usar en JavaScript/TypeScript
const primaryColor = COLORS.primary[600];
const largePadding = SPACING.lg;
```

---

## 📚 Recursos

### Documentación
- [TailwindCSS](https://tailwindcss.com/docs)
- [React TypeScript](https://react.dev/learn/typescript)
- [Heroicons](https://heroicons.com/) - Iconos SVG

### Herramientas
- [Tailwind Color Shades Generator](https://www.tints.dev/)
- [Google Fonts](https://fonts.google.com/)
- [CSS Gradient Generator](https://cssgradient.io/)

---

## 🎯 Checklist de Implementación

Al crear un nuevo componente o página:

- [ ] Usar componentes del sistema (Button, Card, Input, etc.)
- [ ] Aplicar animaciones de entrada (fade-in, slide-up)
- [ ] Implementar responsive design (mobile-first)
- [ ] Agregar estados hover/focus/active
- [ ] Validar accesibilidad (ARIA labels)
- [ ] Usar colores semánticos del sistema
- [ ] Mantener consistencia en espaciados
- [ ] Agregar comentarios JSDoc al componente
- [ ] Exportar desde `components/index.ts`
- [ ] Verificar build sin errores TypeScript

---

**Versión:** 1.0.0  
**Última actualización:** Marzo 2026  
**Desarrollado con ❤️ usando TailwindCSS + React + TypeScript**
