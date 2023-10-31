# Quick Silver/CodeProber wrapper test

A simple CodeProber wrapper around the example "dc" silver program (silver/tutorials/dc).
The wrapper is done in the style of [minimal probe wrapper](https://github.com/lu-cs-sde/codeprober/tree/master/minimal-probe-wrapper).

The integration is very simple. It intercepts messages to standard out during evaluation. If a line is printed with a colon in the middle, it is presented as a property in CodeProber. Everything to the left of the colon is the property name, and everything to the right is the property value.
What this means is that there is no "on-demand" evaluation going on, the full compiler is always executed.
With more time and knowledge of how Silver works, a better integration is likely possible.

Demo video:

https://github.com/Kevlanche/silver-codeprober-wrapper/assets/3907421/d0ce3c13-061e-418a-9b25-229d95bbd903

The video above shows properties being explored in the CodeProber UI. It also shows a "Demo" property appearing after a rebuild.
