package silverwrapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import common.DecoratedNode;
import common.IOToken;
import common.OriginContext;
import dc.Init;
import dc.Pmain;
import silver.core.PevalIO;

public class SilverWrapper {

	public static Object CodeProber_parse(String[] args) throws IOException {
		// Some initialization stuff that seems required
		common.Util.init();
		Init.initAllStatics();
		Init.init();
		Init.postInit();

		final String input = Files.readAllLines(new File(args[args.length - 1]).toPath()).stream()
				.collect(Collectors.joining("\n"));
		final DecoratedNode decorated = PevalIO.invoke( //
				OriginContext.ENTRY_CONTEXT,
				Pmain.invoke(OriginContext.ENTRY_CONTEXT, dc.Main.cvargs(new String[] { input })), //
				IOToken.singleton //
		).decorate();

		final PrintStream prevOut = System.out;
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			System.setOut(new PrintStream(baos));
			decorated.synthesized(dc.Init.dc_Main_sv_25_18_args__ON__dc_main);
		} finally {
			System.out.flush();
			System.setOut(prevOut);
		}
		final String output = baos.toString(StandardCharsets.UTF_8);
		final SilverWrapper sw = new SilverWrapper();
		for (String part : output.split("\n\n")) {
			part = part.trim();
			if (part.length() == 0) {
				continue;
			}
			final int separator = part.indexOf(':');
			if (separator <= 0 || separator == part.length() - 1) {
				continue;
			}
			final String key = part.substring(0, separator).trim();
			final String value = part.substring(separator + 1).trim();
			sw.addAttr(new AttributeIsh(key, value));
		}
		return sw;
	}

	private static class AttributeIsh {
		public final String key;
		public final String val;

		public AttributeIsh(String key, String val) {
			this.key = key;
			this.val = val;
		}
	}

	private List<AttributeIsh> attributes = new ArrayList<>();

	private void addAttr(AttributeIsh attr) {
		attributes.add(attr);
	}

	public List<String> cpr_propertyListShow() {
		return attributes.stream().map(a -> String.format("l:%s", a.key)).collect(Collectors.toList());
	}

	public Object cpr_lInvoke(String attr) {
		for (AttributeIsh a : attributes) {
			if (a.key.equals(attr)) {
				return a.val;
			}
		}
		throw new IllegalArgumentException("Invalid attribute '" + attr + "'");
	}

	public int getStart() {
		return (1 << 12) + 1;
	}

	public int getEnd() {
		return (999 << 12) + 1;
	}

	public int getNumChild() {
		return 0;
	}

	public Object getChild(int i) {
		return null;
	}

	public Object getParent() {
		return null;
	}

	public static void main(String[] args) {
		dc.Main.main(args);
	}
}
