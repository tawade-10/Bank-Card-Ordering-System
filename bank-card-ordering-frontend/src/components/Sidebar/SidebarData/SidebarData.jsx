import DashboardIcon from "@mui/icons-material/Dashboard";
import CreditCardIcon from "@mui/icons-material/CreditCard";
import AccountBalanceIcon from "@mui/icons-material/AccountBalance";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import ReceiptLongIcon from "@mui/icons-material/ReceiptLong";
import LogoutIcon from "@mui/icons-material/Logout";

const SidebarData = [
  {
    title: "Dashboard",
    icon: <DashboardIcon />,
    link: "/admin/dashboard",
  },
  {
    title: "Card Requests",
    icon: <CreditCardIcon />,
    link: "/admin/card-requests",
  },
  {
    title: "Account Requests",
    icon: <AccountBalanceIcon />,
    link: "/admin/account-requests",
  },
  {
    title: "Customers",
    icon: <PeopleAltIcon />,
    link: "/admin/customers",
  },
  {
    title: "Reports",
    icon: <ReceiptLongIcon />,
    link: "/admin/reports",
  },
  {
    title: "Logout",
    icon: <LogoutIcon />,
    link: "/logout",
  },
];

export default SideBarData;