package zulu;

public enum Architecture {
	x86 {
		private final String[] nomPotentiel = {"x86","i686","i586","i486"};
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return getArch();
		}

		@Override
		public int getBitness() {
			// TODO Auto-generated method stub
			return 32;
		}
		@Override
		public String getArch() {
			// TODO Auto-generated method stub
			return "x86";
		}

		@Override
		public final String[] getNomPotentiel() {
			// TODO Auto-generated method stub
			return nomPotentiel;
		}
	},
	x86_64 {
		private final String[] nomPotentiel = {"x86_64","amd64"};
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return getArch()+"_"+getBitness();
		}

		@Override
		public int getBitness() {
			// TODO Auto-generated method stub
			return 64;
		}

		@Override
		public String getArch() {
			// TODO Auto-generated method stub
			return "x86";
		}
		@Override
		public final String[] getNomPotentiel() {
			// TODO Auto-generated method stub
			return nomPotentiel;
		}
	},
	arm32 {
		private final String[] nomPotentiel = {"aarch32"};
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return getArch()+getBitness();
		}

		@Override
		public int getBitness() {
			// TODO Auto-generated method stub
			return 32;
		}

		@Override
		public String getArch() {
			// TODO Auto-generated method stub
			return "arm";
		}
		@Override
		public final String[] getNomPotentiel() {
			// TODO Auto-generated method stub
			return nomPotentiel;
		}
	},
	arm64 {
		private final String[] nomPotentiel = {"aarch64"};
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return getArch()+getBitness();
		}

		@Override
		public int getBitness() {
			// TODO Auto-generated method stub
			return 64;
		}

		@Override
		public String getArch() {
			// TODO Auto-generated method stub
			return "arm";
		}
		@Override
		public final String[] getNomPotentiel() {
			// TODO Auto-generated method stub
			return nomPotentiel;
		}
	};
	public abstract String getNom();
	public abstract int getBitness();
	public abstract String getArch();
	public abstract String[] getNomPotentiel();
}
