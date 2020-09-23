package zulu;

public enum JavaVersion {
	java_8 {
		@Override
		public String getDureeDeVie() {
			// TODO Auto-generated method stub
			return "LTS";
		}

		@Override
		public int getVersion() {
			// TODO Auto-generated method stub
			return 8;
		}
	},
	java_11 {
		@Override
		public String getDureeDeVie() {
			// TODO Auto-generated method stub
			return "LTS";
		}

		@Override
		public int getVersion() {
			// TODO Auto-generated method stub
			return 11;
		}
	},
	java_13 {
		@Override
		public String getDureeDeVie() {
			// TODO Auto-generated method stub
			return "MTS";
		}

		@Override
		public int getVersion() {
			// TODO Auto-generated method stub
			return 13;
		}
	},
	java_14 {
		@Override
		public String getDureeDeVie() {
			// TODO Auto-generated method stub
			return "";
		}

		@Override
		public int getVersion() {
			// TODO Auto-generated method stub
			return 14;
		}
	},
	java_15 {
		@Override
		public String getDureeDeVie() {
			// TODO Auto-generated method stub
			return "MTS";
		}

		@Override
		public int getVersion() {
			// TODO Auto-generated method stub
			return 15;
		}
	};
	public abstract String getDureeDeVie();
	public abstract int getVersion();
}
