import { ReactNode } from "react";
import { CheckboxContext } from "./checkboxContext";
import { styled } from "styled-components";

export default function CheckboxGroup({
  children,
  values,
  onChange,
}: {
  children: ReactNode;
  values: string[];
  onChange: (values: string[]) => void;
}) {
  const isChecked = (value: string) => values.includes(value);
  const toggleValue = (checked: boolean, value: string) => {
    if (checked) {
      onChange(values.concat(value));
    } else {
      onChange(values.filter((v) => v !== value));
    }
  };

  return (
    <Fieldset>
      <CheckboxContext.Provider value={{ isChecked, toggleValue }}>
        {children}
      </CheckboxContext.Provider>
    </Fieldset>
  );
}

const Fieldset = styled.fieldset`
  border: none;
  margin: 0;
  padding: 0;
  
`
