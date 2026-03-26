import { type ReactNode } from "react";

interface TableProps {
  children: ReactNode;
  className?: string;
}

export const Table = ({ children, className = "" }: TableProps) => {
  return (
    <div className="overflow-x-auto">
      <table className={`min-w-full divide-y divide-gray-200 ${className}`.trim()}>
        {children}
      </table>
    </div>
  );
};

interface TableHeaderProps {
  children: ReactNode;
}

export const TableHeader = ({ children }: TableHeaderProps) => {
  return <thead className="bg-gray-50">{children}</thead>;
};

interface TableBodyProps {
  children: ReactNode;
}

export const TableBody = ({ children }: TableBodyProps) => {
  return <tbody className="bg-white divide-y divide-gray-200">{children}</tbody>;
};

interface TableRowProps {
  children: ReactNode;
  onClick?: () => void;
  className?: string;
}

export const TableRow = ({ children, onClick, className = "" }: TableRowProps) => {
  const baseStyles = onClick ? "cursor-pointer hover:bg-gray-50 transition-colors" : "";
  return (
    <tr className={`${baseStyles} ${className}`.trim()} onClick={onClick}>
      {children}
    </tr>
  );
};

interface TableHeadProps {
  children: ReactNode;
  className?: string;
}

export const TableHead = ({ children, className = "" }: TableHeadProps) => {
  return (
    <th
      className={`px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider ${className}`.trim()}
    >
      {children}
    </th>
  );
};

interface TableCellProps {
  children: ReactNode;
  className?: string;
}

export const TableCell = ({ children, className = "" }: TableCellProps) => {
  return (
    <td className={`px-6 py-4 whitespace-nowrap text-sm text-gray-900 ${className}`.trim()}>
      {children}
    </td>
  );
};
