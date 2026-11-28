/* SystemJS module definition */
declare var module: NodeModule;
interface NodeModule {
  id: string;
}

interface Navigator {
  msSaveOrOpenBlob?: (blob: any, defaultName?: string) => boolean;
}
