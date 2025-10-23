// Ubicaciones
export interface UbicacionResp {
  ubiId: number;
  ubiPais: string;
  ubiRegion: string;
  ubiCiudad: string;
  ubiLatitud?: number | null;
  ubiLongitud?: number | null;
}

export interface PerfilCrear {
  perFoto: string | null;
  perUsuarioId: number;
  ubiId: number;
}

export interface PerfilResp {
  perId: number;
  perUsuarioId: number;
  perFoto: string | null;
  perFechaCreacion: string;
  perUltimaActualizacion: string;
  perEstadoCuestionario: boolean;
  ubiId: number;
  ubiPais: string;
  ubiRegion: string;
  ubiCiudad: string;
  ubiLatitud?: number | null;
  ubiLongitud?: number | null;
  cueId: number | null;
  cueEstado?: string | null;
}

export interface CuestionarioCrear {
  perId: number;
  cueNombre?: string | null;
}

export interface CuestionarioResp {
  cueId: number;
  perId: number | null;
  cueNombre: string;
  cueFechaCreacion: string;
  cueActivo: boolean;
  preguntas: PreguntaResp[];
}

export interface PreguntaResp {
  preId: number;
  preEnunciado: string;
  preObligatoria: boolean;
  alternativas: Array<{ altId: number; altOpcion: string }>;
}

export interface RespuestaGuardar {
  perId: number;
  cueId: number;
  preId: number;
  altIds: number[];
}
