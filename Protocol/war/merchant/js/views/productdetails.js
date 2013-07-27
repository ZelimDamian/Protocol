directory.ProductView = Backbone.View.extend({

    render: function () {
    	this.model = new directory.Product();
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }
});

directory.ProductSummaryView = Backbone.View.extend({

    initialize:function (options) {
    	_.bindAll(this);
    	this.model = new directory.Product();
        this.model.on("change", this.render, this);
        this.collection = options.collection;
    },

    render:function () {
        this.$el.html(this.template(this.model.toJSON()));
        
        $login = this.$el.find('#login');
        $account = this.$el.find('#account');
        return this;
    },

    updateSaveModel:function()
    {
    	var data = {
    			login : $login.val(),
    			account: parseInt($account.val())
    	};
    	this.model.save(data);
    	this.collection.add(this.model);
    },

    setModel:function(newModel)
    {
    	this.model = newModel;
    	this.render();
    },
    
    generatePayment: function()
    {
    	
    },
    
    events: {
    	'click #generateButton': this.generatePayment
    }

});