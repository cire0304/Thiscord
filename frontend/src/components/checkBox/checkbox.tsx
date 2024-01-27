import React, { useContext } from "react";
import styled from "styled-components";
import { CheckboxContext } from "./checkboxContext";



export default function Checkbox({
  children,
  value,
  checked,
  onChange,
}: {
  children: React.ReactNode;
  value: string;
  checked: boolean;
  onChange?: (checked: boolean) => void;
}) {
  const context = useContext(CheckboxContext);
  if (!context) {
    return (
      <label>
        <Input
          type="checkbox"
          checked={checked}
          onChange={({ target: { checked } }) => {
            if (onChange) onChange(checked)}
          }
        />
        {children}
      </label>
    );
  }

  const { isChecked, toggleValue } = context;
  return (
    <label>
      <Input
        type="checkbox"
        checked={isChecked(value)}
        onChange={({ target: { checked } }) => toggleValue(checked, value)}
      />
      {children}
    </label>
  );
}

const Input = styled.input`
  visibility: hidden;
`
