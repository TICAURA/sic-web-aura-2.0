package mx.com.consolida.dto; 

import java.io.Serializable;

import mx.com.consolida.catalogos.CatImssDto;
import mx.com.consolida.catalogos.CatSalariosGeneralesDto;

public class CatatologosCotizadorDto implements Serializable {

	    private static final long serialVersionUID = 1L;
	    
	    private CatSalariosGeneralesDto smg;
		private CatSalariosGeneralesDto smgzf;
		private CatSalariosGeneralesDto smgv;
		private CatSalariosGeneralesDto uma;
		private CatSalariosGeneralesDto isn;

		private CatImssDto cfp;
		private CatImssDto ep;
		private CatImssDto et;
		private CatImssDto gmp;
		private CatImssDto gmt;
		private CatImssDto pdp;
		private CatImssDto pdt;
		private CatImssDto ivp;
		private CatImssDto ivt;
		private CatImssDto pdgp;
		private CatImssDto rp;
		private CatImssDto cvp;
		private CatImssDto cvt;
		private CatImssDto aip;
		
		public CatSalariosGeneralesDto getSmg() {
			return smg;
		}
		public void setSmg(CatSalariosGeneralesDto smg) {
			this.smg = smg;
		}
		public CatSalariosGeneralesDto getSmgzf() {
			return smgzf;
		}
		public void setSmgzf(CatSalariosGeneralesDto smgzf) {
			this.smgzf = smgzf;
		}
		public CatSalariosGeneralesDto getSmgv() {
			return smgv;
		}
		public void setSmgv(CatSalariosGeneralesDto smgv) {
			this.smgv = smgv;
		}
		public CatSalariosGeneralesDto getUma() {
			return uma;
		}
		public void setUma(CatSalariosGeneralesDto uma) {
			this.uma = uma;
		}
		public CatSalariosGeneralesDto getIsn() {
			return isn;
		}
		public void setIsn(CatSalariosGeneralesDto isn) {
			this.isn = isn;
		}
		public CatImssDto getCfp() {
			return cfp;
		}
		public void setCfp(CatImssDto cfp) {
			this.cfp = cfp;
		}
		public CatImssDto getEp() {
			return ep;
		}
		public void setEp(CatImssDto ep) {
			this.ep = ep;
		}
		public CatImssDto getEt() {
			return et;
		}
		public void setEt(CatImssDto et) {
			this.et = et;
		}
		public CatImssDto getGmp() {
			return gmp;
		}
		public void setGmp(CatImssDto gmp) {
			this.gmp = gmp;
		}
		public CatImssDto getGmt() {
			return gmt;
		}
		public void setGmt(CatImssDto gmt) {
			this.gmt = gmt;
		}
		public CatImssDto getPdp() {
			return pdp;
		}
		public void setPdp(CatImssDto pdp) {
			this.pdp = pdp;
		}
		public CatImssDto getPdt() {
			return pdt;
		}
		public void setPdt(CatImssDto pdt) {
			this.pdt = pdt;
		}
		public CatImssDto getIvp() {
			return ivp;
		}
		public void setIvp(CatImssDto ivp) {
			this.ivp = ivp;
		}
		public CatImssDto getIvt() {
			return ivt;
		}
		public void setIvt(CatImssDto ivt) {
			this.ivt = ivt;
		}
		public CatImssDto getPdgp() {
			return pdgp;
		}
		public void setPdgp(CatImssDto pdgp) {
			this.pdgp = pdgp;
		}
		public CatImssDto getRp() {
			return rp;
		}
		public void setRp(CatImssDto rp) {
			this.rp = rp;
		}
		public CatImssDto getCvp() {
			return cvp;
		}
		public void setCvp(CatImssDto cvp) {
			this.cvp = cvp;
		}
		public CatImssDto getCvt() {
			return cvt;
		}
		public void setCvt(CatImssDto cvt) {
			this.cvt = cvt;
		}
		public CatImssDto getAip() {
			return aip;
		}
		public void setAip(CatImssDto aip) {
			this.aip = aip;
		}
		
}
