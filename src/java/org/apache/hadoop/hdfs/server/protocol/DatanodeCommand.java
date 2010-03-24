/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.server.protocol;

import java.io.DataInput;
import java.io.DataOutput;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableFactory;
import org.apache.hadoop.io.WritableFactories;
import org.apache.avro.reflect.Union;

/**
 * Base class for data-node command.
 * Issued by the name-node to notify data-nodes what should be done.
 */

// Declare subclasses for Avro's denormalized representation
@Union({Void.class,
      DatanodeCommand.Register.class, DatanodeCommand.Finalize.class,
      BlockCommand.class, UpgradeCommand.class,
      BlockRecoveryCommand.class, KeyUpdateCommand.class})

public abstract class DatanodeCommand extends ServerCommand {
  static class Register extends DatanodeCommand {
    private Register() {super(DatanodeProtocol.DNA_REGISTER);}
    public void readFields(DataInput in) {}
    public void write(DataOutput out) {}
  }

  static class Finalize extends DatanodeCommand {
    private Finalize() {super(DatanodeProtocol.DNA_FINALIZE);}
    public void readFields(DataInput in) {}
    public void write(DataOutput out) {}
  }

  static {                                      // register a ctor
    WritableFactories.setFactory(Register.class,
        new WritableFactory() {
          public Writable newInstance() {return new Register();}
        });
    WritableFactories.setFactory(Finalize.class,
        new WritableFactory() {
          public Writable newInstance() {return new Finalize();}
        });
  }

  public static final DatanodeCommand REGISTER = new Register();
  public static final DatanodeCommand FINALIZE = new Finalize();
  
  public DatanodeCommand() {
    super();
  }
  
  DatanodeCommand(int action) {
    super(action);
  }
}
