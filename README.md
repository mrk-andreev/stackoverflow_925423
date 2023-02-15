# Benchmark

```
package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(1)
public class StringBenchmark {
    private String foo;

    private String bar;

    @Setup(value = Level.Iteration)
    public void setup() {
        foo = UUID.randomUUID().toString();
        bar = UUID.randomUUID().toString();
    }

    @Benchmark
    public void concat(Blackhole blackhole) {
        blackhole.consume(foo + "_" + bar);
    }

    @Benchmark
    public void fmt(Blackhole blackhole) {
        blackhole.consume(String.format("%s_%s", foo, bar));
    }

    @Benchmark
    public void stringBuilder(Blackhole blackhole) {
        blackhole.consume(new StringBuilder().append(foo).append("_").append(bar).toString());
    }
}
```



# Results

```
Benchmark                      Mode  Cnt    Score   Error  Units
StringBenchmark.concat         avgt    5   15.223 ± 1.122  ns/op
StringBenchmark.fmt            avgt    5  146.616 ± 6.568  ns/op
StringBenchmark.stringBuilder  avgt    5   13.352 ± 1.244  ns/op
```


```
Result "org.example.StringBenchmark.concat":
  15.223 ±(99.9%) 1.122 ns/op [Average]
  (min, avg, max) = (14.903, 15.223, 15.446), stdev = 0.291
  CI (99.9%): [14.101, 16.344] (assumes normal distribution)

Result "org.example.StringBenchmark.fmt":
  146.616 ±(99.9%) 6.568 ns/op [Average]
  (min, avg, max) = (144.275, 146.616, 148.309), stdev = 1.706
  CI (99.9%): [140.048, 153.185] (assumes normal distribution)

Result "org.example.StringBenchmark.stringBuilder":
  13.352 ±(99.9%) 1.244 ns/op [Average]
  (min, avg, max) = (12.853, 13.352, 13.732), stdev = 0.323
  CI (99.9%): [12.109, 14.596] (assumes normal distribution)
```

```
REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
extra caution when trusting the results, look into the generated code to check the benchmark still
works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
different JVMs are already problematic, the performance difference caused by different Blackhole
modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.
```
