import { ReactNode } from "react";

import { useNavigate } from "react-router-dom";
import { useAppSelector } from "../hooks/redux";

export default function UserGuard({ children }: { children: ReactNode }) {
  const user = useAppSelector((state) => state.user);
  const navigate = useNavigate();
  if (user.id === 0) {
    navigate("/login");
  }

  return <>{children}</>;
}
