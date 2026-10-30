interface NavAttributes {
  [propName: string]: any;
}
interface NavWrapper {
  attributes: NavAttributes;
  element: string;
}
interface NavBadge {
  text: string;
  variant: string;
}
interface NavLabel {
  class?: string;
  variant: string;
}

export interface NavData {
  name?: string;
  url?: string;
  icon?: string;
  badge?: NavBadge;
  title?: boolean;
  children?: NavData[];
  variant?: string;
  attributes?: NavAttributes;
  divider?: boolean;
  class?: string;
  label?: NavLabel;
  wrapper?: NavWrapper;
}
export const exicomNavItems: NavData[] = [
  {
    name: 'Home',
    url: '/home',
    icon: 'icon-speedometer'
  },
  {
    name: 'Master',
    children: [
      {
        name: 'Accounts',
        url: '/accounts'
      },
      {
        name: 'Contacts',
        url: '/contacts'
      },
      {
        name: 'Orders',
        url: '/orders'
      },
      {
        name: 'Sites',
        url: '/sites'
      },
      {
        name: 'Products',
        url: '/products'
      },
      {
        name: 'Assets',
        url: '/assets'
      },
      {
        name: 'Legal Entity',
        url: '/legalentity'
      },
      {
        name: 'City',
        url: '/city'
      },
      {
        name: 'Zone',
        url: '/zone'
      },
      {
        name: 'Warehouse',
        url: '/warehouse'
      },
      {
        name: 'Circles',
        url: '/circles'
      },

      {
        name: 'Expenses Management',
        url: '/expensesmanagement'
      },
      {
        name: 'Delivery Challan',
        url: '/deliverychallan'
      },

      {
        name: 'Work Orders',
        url: '/workOrder'
      },

      {
        name: 'Service Reports',
        url: '/serviceReport'
      },
      {
        name: 'Requisition',
        url: '/requisition'
      },
      {
        name: 'System Configuration Parameters',
        url: '/systemConfigurationParameters'
      },
      {
        name: 'Job Warranty',
        url: '/jobWarranty'
      },

      {
        name: 'Service Resources',
        url: '/serviceResource'
      },
      {
        name: 'batteryFSRCBMS',
        url: '/Battery FSR CBMS & Li-lon Battery'
      },

      {
        name: 'Bording/Lodging, DA',
        url: '/Bording_lodging_DA',
      },
      {
        name: 'Location',
        url: '/Location'
      },
      {
        name: 'Approval',
        url: '/approval'
      }
    ]
  },
  {
    name: 'Call Center',
    icon: '',
    children: [
      {
        name: 'Case',
        url: '/cases'
      },
      // {
      //   name: 'Opportunity Case',
      //   url: '/opportunitycase'
      // },
      {
        name: 'Opportunities',
        url: '/Opportunities'
      },
      {
        name: 'Quote',
        url: '/Quote'
      },
    ]
  },
  {
    name: 'FSL',
    children: [
      {
        name: 'Service Appointments',
        url: '/serviceappointment'
      },
      {
        name: 'MSL Request',
        url: '/Mslrequest'
      },
      {
        name: 'Battery Inventory Consumptions',
        url: '/batteryinventoryconsumption'
      },


    ]
  },
  {
    name: 'Inventory',
    children: [

      {
        name: 'Manage Inventory',
        url: '/manageInventory'
      },
      {
        name: 'Sub Product Inventory',
        url: '/subProductInventory'
      },
      {
        name: 'Product Line Items',
        url: '/productLineItems'
      },
      {
        name: 'Issued Components',
        url: '/issuedComponent'
      },
      {
        name: 'Manage Components',
        url: '/manageComponent'
      },
      {
        name: 'Repair Engineer',
        url: '/repairengineer'
      }
    ]
  }
]

export const navItems: NavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    icon: 'icon-speedometer',
    badge: {
      variant: 'info',
      text: 'NEW'
    }
  },
  {
    title: true,
    name: 'Theme'
  },
  {
    name: 'Colors',
    url: '/theme/colors',
    icon: 'icon-drop'
  },
  {
    name: 'Typography',
    url: '/theme/typography',
    icon: 'icon-pencil'
  },
  {
    title: true,
    name: 'Components'
  },
  {
    name: 'Base',
    url: '/base',
    icon: 'icon-puzzle',
    children: [
      {
        name: 'Cards',
        url: '/base/cards',
        icon: 'icon-puzzle'
      },
      {
        name: 'Carousels',
        url: '/base/carousels',
        icon: 'icon-puzzle'
      },
      {
        name: 'Collapses',
        url: '/base/collapses',
        icon: 'icon-puzzle'
      },
      {
        name: 'Forms',
        url: '/base/forms',
        icon: 'icon-puzzle'
      },
      {
        name: 'Pagination',
        url: '/base/paginations',
        icon: 'icon-puzzle'
      },
      {
        name: 'Popovers',
        url: '/base/popovers',
        icon: 'icon-puzzle'
      },
      {
        name: 'Progress',
        url: '/base/progress',
        icon: 'icon-puzzle'
      },
      {
        name: 'Switches',
        url: '/base/switches',
        icon: 'icon-puzzle'
      },
      {
        name: 'Tables',
        url: '/base/tables',
        icon: 'icon-puzzle'
      },
      {
        name: 'Tabs',
        url: '/base/tabs',
        icon: 'icon-puzzle'
      },
      {
        name: 'Tooltips',
        url: '/base/tooltips',
        icon: 'icon-puzzle'
      }
    ]
  },
  {
    name: 'Buttons',
    url: '/buttons',
    icon: 'icon-cursor',
    children: [
      {
        name: 'Buttons',
        url: '/buttons/buttons',
        icon: 'icon-cursor'
      },
      {
        name: 'Dropdowns',
        url: '/buttons/dropdowns',
        icon: 'icon-cursor'
      },
      {
        name: 'Brand Buttons',
        url: '/buttons/brand-buttons',
        icon: 'icon-cursor'
      }
    ]
  },
  {
    name: 'Charts',
    url: '/charts',
    icon: 'icon-pie-chart'
  },
  {
    name: 'Icons',
    url: '/icons',
    icon: 'icon-star',
    children: [
      {
        name: 'CoreUI Icons',
        url: '/icons/coreui-icons',
        icon: 'icon-star',
        badge: {
          variant: 'success',
          text: 'NEW'
        }
      },
      {
        name: 'Flags',
        url: '/icons/flags',
        icon: 'icon-star'
      },
      {
        name: 'Font Awesome',
        url: '/icons/font-awesome',
        icon: 'icon-star',
        badge: {
          variant: 'secondary',
          text: '4.7'
        }
      },
      {
        name: 'Simple Line Icons',
        url: '/icons/simple-line-icons',
        icon: 'icon-star'
      }
    ]
  },
  {
    name: 'Notifications',
    url: '/notifications',
    icon: 'icon-bell',
    children: [
      {
        name: 'Alerts',
        url: '/notifications/alerts',
        icon: 'icon-bell'
      },
      {
        name: 'Badges',
        url: '/notifications/badges',
        icon: 'icon-bell'
      },
      {
        name: 'Modals',
        url: '/notifications/modals',
        icon: 'icon-bell'
      }
    ]
  },
  {
    name: 'Widgets',
    url: '/widgets',
    icon: 'icon-calculator',
    badge: {
      variant: 'info',
      text: 'NEW'
    }
  },
  {
    divider: true
  },
  {
    title: true,
    name: 'Extras',
  },
  {
    name: 'Pages',
    url: '/pages',
    icon: 'icon-star',
    children: [
      {
        name: 'Login',
        url: '/login',
        icon: 'icon-star'
      },
      {
        name: 'Register',
        url: '/register',
        icon: 'icon-star'
      },
      {
        name: 'Error 404',
        url: '/404',
        icon: 'icon-star'
      },
      {
        name: 'Error 500',
        url: '/500',
        icon: 'icon-star'
      }
    ]
  },
  {
    name: 'Disabled',
    url: '/dashboard',
    icon: 'icon-ban',
    badge: {
      variant: 'secondary',
      text: 'NEW'
    },
    attributes: { disabled: true },
  },
  {
    name: 'Download CoreUI',
    url: 'http://coreui.io/angular/',
    icon: 'icon-cloud-download',
    class: 'mt-auto',
    variant: 'success',
    attributes: { target: '_blank', rel: 'noopener' }
  },
  {
    name: 'Try CoreUI PRO',
    url: 'http://coreui.io/pro/angular/',
    icon: 'icon-layers',
    variant: 'danger',
    attributes: { target: '_blank', rel: 'noopener' }
  }
];
