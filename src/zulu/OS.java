package zulu;

public enum OS {
	Windows {
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "Windows";
		}
	},
	MacOS {
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "MacOS";
		}
	},
	Linux
	{
		@Override
		public String getNom() {
			// TODO Auto-generated method stub
			return "Linux";
		}
	};
	public abstract String getNom();
}
