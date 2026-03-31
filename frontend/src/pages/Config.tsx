import { type FormEvent, useEffect, useState } from "react";
import {
  Button,
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardFooter,
  Input,
  PageHeader,
  LoadingSpinner,
  ErrorMessage,
} from "../components";
import { useConfig } from "../hooks";
import { COUNTRY_CODES } from "../utils";

const Config = () => {
  const { config, isLoading, isSaving, error, success, fetchConfig, saveConfig } =
    useConfig();

  const [maxAmountThreshold, setMaxAmountThreshold] = useState<number>(0);
  const [userCountry, setUserCountry] = useState<string>("");

  useEffect(() => {
    fetchConfig();
  }, []);

  useEffect(() => {
    setMaxAmountThreshold(config.maxAmountThreshold);
    setUserCountry(config.userCountry);
  }, [config]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    await saveConfig({ maxAmountThreshold, userCountry });
  };

  if (isLoading) {
    return (
      <div className="animate-fade-in">
        <PageHeader
          title="Configuración"
          subtitle="Administra los parámetros del sistema de detección de fraude"
          icon={
            <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.066 2.573c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.573 1.066c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.066-2.573c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
          }
        />
        <LoadingSpinner message="Cargando configuración..." />
      </div>
    );
  }

  return (
    <div className="animate-fade-in">
      <PageHeader
        title="Configuración"
        subtitle="Administra los parámetros del sistema de detección de fraude"
        icon={
          <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.066 2.573c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.573 1.066c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.066-2.573c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
        }
      />

      <div className="max-w-2xl mx-auto">
        <Card shadow="lg" hoverable>
          <CardHeader>
            <CardTitle size="lg">Parámetros de Detección</CardTitle>
          </CardHeader>
          <CardContent>
            {error && (
              <div className="mb-5">
                <ErrorMessage message={error} retry={fetchConfig} />
              </div>
            )}

            {success && (
              <div className="mb-5 bg-gradient-to-br from-success-50 to-success-100 border-2 border-success-300 rounded-xl p-4 animate-fade-in">
                <div className="flex items-center gap-3">
                  <svg className="w-5 h-5 text-success-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                  <p className="text-success-800 font-semibold text-sm">{success}</p>
                </div>
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-5">
              <Input
                label="Umbral Máximo de Monto"
                type="number"
                value={maxAmountThreshold}
                onChange={(e) => setMaxAmountThreshold(Number(e.target.value))}
                required
                min="0"
                step="0.01"
                placeholder="15000"
                helperText="Transacciones por encima de este monto serán marcadas como sospechosas"
                prefixIcon={
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                }
              />

              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">
                  País del Usuario
                  <span className="text-danger-500 ml-1">*</span>
                </label>
                <div className="relative">
                  <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                  </div>
                  <select
                    value={userCountry}
                    onChange={(e) => setUserCountry(e.target.value)}
                    className="w-full pl-10 px-4 py-2.5 border border-gray-300 rounded-lg bg-white focus:ring-4 focus:ring-primary-100 focus:border-primary-500 outline-none transition-all hover:border-gray-400"
                    required
                  >
                    <option value="">Seleccionar país...</option>
                    {COUNTRY_CODES.map((country) => (
                      <option key={country.code} value={country.code}>
                        {country.name} ({country.code})
                      </option>
                    ))}
                  </select>
                </div>
                <p className="mt-1.5 text-xs text-gray-500">
                  Transacciones desde un país diferente serán marcadas como ubicación inusual
                </p>
              </div>
            </form>
          </CardContent>

          <CardFooter>
            <Button
              type="submit"
              onClick={handleSubmit}
              isLoading={isSaving}
              fullWidth
              size="md"
            >
              <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
              Guardar Configuración
            </Button>
          </CardFooter>
        </Card>
      </div>
    </div>
  );
};

export default Config;
