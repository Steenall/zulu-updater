package zulu;

public enum JavaPackage {
	JDK {
		@Override
		public boolean possedeFX() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean estJDK() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "JDK";
		}
	},
	JDK_FX {
		@Override
		public boolean possedeFX() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean estJDK() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "JDK_FX";
		}
	},
	JRE {
		@Override
		public boolean possedeFX() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean estJDK() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "JRE";
		}
	},
	JRE_FX {
		@Override
		public boolean possedeFX() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean estJDK() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "JRE_FX";
		}
	};
	public abstract boolean possedeFX();
	public abstract boolean estJDK();
	public abstract String getNom();
}
