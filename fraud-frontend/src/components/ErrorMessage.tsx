interface ErrorMessageProps {
  message: string;
  retry?: () => void;
}

export const ErrorMessage = ({ message, retry }: ErrorMessageProps) => {
  return (
    <div className="max-w-2xl mx-auto p-6">
      <div className="bg-red-50 border border-red-200 rounded-lg p-6">
        <div className="flex items-start">
          <svg
            className="w-6 h-6 text-red-600 mt-0.5"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <div className="ml-3 flex-1">
            <h3 className="text-sm font-medium text-red-800">
              Ha ocurrido un error
            </h3>
            <p className="mt-2 text-sm text-red-700">{message}</p>
            {retry && (
              <button
                onClick={retry}
                className="mt-3 text-sm font-medium text-red-600 hover:text-red-500"
              >
                Intentar nuevamente
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
