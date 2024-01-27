import { createContext } from "react";

interface CheckboxContextType{
    isChecked: (value: string) => boolean;
    toggleValue: (checked: boolean, value: string) => void;
}

export const CheckboxContext = createContext<CheckboxContextType | null>( null);
