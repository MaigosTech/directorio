package directorio.models.session;

import java.util.Date;

public class ObjSesion {
		public String usuario;
		public Date hora;
		public String ip;
		
		public ObjSesion(String usuario, Date hora, String ip) {
			this.usuario = usuario;
			this.hora = hora;
			this.ip = ip;
		}
		
		public String toString() {
			return usuario + " / " + hora + ", " + 	ip;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public Date getHora() {
			return hora;
		}

		public void setHora(Date hora) {
			this.hora = hora;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

}
